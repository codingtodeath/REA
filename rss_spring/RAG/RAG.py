import openai
import os
# 创建
from haystack.document_stores.in_memory import InMemoryDocumentStore
document_store = InMemoryDocumentStore()

# 导入文档
# 示例文档，包含自定义字段
from haystack import Document
import PyPDF2
def read_files(file_path):
    text = ""
    with open(file_path, "rb") as file:
        reader = PyPDF2.PdfReader(file)
        for page in reader.pages:
            text += page.extract_text() + "\n"
    return text
filedir="../saveDocs/Articles/"
file_contents = {}
# 列出文件夹中的所有文件
default_meta={
            "author": "John Doe",
            "category": "AI Research",
            "publication_date": "2024-10-01"
        }
dataset = [
    {
        "content": "OpenAI is revolutionizing AI research. Liao Yefei is not a fool and he likes playing basketball.",
        "meta": {
            "author": "John Doe",
            "category": "AI Research",
            "publication_date": "2024-10-01"
        }
    },
    {
        "content": "Elasticsearch is a distributed search engine.Qiu Jintao does not like blue as he is ofen bule.",
        "meta": {
            "author": "Jane Smith",
            "category": "Search Engines",
            "publication_date": "2023-09-15"
        }
    }
]
for filename in os.listdir(filedir):
    file_path = os.path.join(filedir, filename)
    # 检查是否为文件（而不是文件夹）
    if os.path.isfile(file_path):
        content=read_files(file_path)
        file_contents["content"] = content  # 存储文件名及其内容
        file_contents["meta"] = default_meta
        dataset.append(file_contents)
        file_contents={}

docs = [Document(content=doc["content"], meta=doc["meta"]) for doc in dataset]
# 编码
from haystack.components.embedders import SentenceTransformersDocumentEmbedder

doc_embedder = SentenceTransformersDocumentEmbedder(model="sentence-transformers/all-MiniLM-L6-v2")
doc_embedder.warm_up()

# 写入
docs_with_embeddings = doc_embedder.run(docs)
document_store.write_documents(docs_with_embeddings["documents"])

# 文本编码器
from haystack.components.embedders import SentenceTransformersTextEmbedder
text_embedder = SentenceTransformersTextEmbedder(model="sentence-transformers/all-MiniLM-L6-v2")

# 查询器
from haystack.components.retrievers.in_memory import InMemoryEmbeddingRetriever
retriever = InMemoryEmbeddingRetriever(document_store)

# 模板提示
from haystack.components.builders import PromptBuilder

template = """
Given the following information, answer the question.

Context:
{% for document in documents %}
    {{ document.content }}
{% endfor %}

Question: {{question}}
Answer:
"""

prompt_builder = PromptBuilder(template=template)

# 大模型回答生成器
import os
from getpass import getpass
from haystack.components.generators import OpenAIGenerator

if "OPENAI_API_KEY" not in os.environ:
    os.environ["OPENAI_API_KEY"] = getpass("")
generator = OpenAIGenerator(model="gpt-3.5-turbo")


# 流水线
from haystack import Pipeline

basic_rag_pipeline = Pipeline()
# Add components to your pipeline
# 编码 检索 构造提示词 生成
basic_rag_pipeline.add_component("text_embedder", text_embedder)
basic_rag_pipeline.add_component("retriever", retriever)
basic_rag_pipeline.add_component("prompt_builder", prompt_builder)
basic_rag_pipeline.add_component("llm", generator)

# Now, connect the components to each other
basic_rag_pipeline.connect("text_embedder.embedding", "retriever.query_embedding")
basic_rag_pipeline.connect("retriever", "prompt_builder.documents")
basic_rag_pipeline.connect("prompt_builder", "llm")

# # 示例
# question = "What sports does Liao Yefei like?"
# response = basic_rag_pipeline.run({"text_embedder": {"text": question}, "prompt_builder": {"question": question}})
# print(response["llm"]["replies"][0])

def generate_answer(query):
    response = basic_rag_pipeline.run({"text_embedder": {"text": query}, "prompt_builder": {"question": query}})
    # print(response["llm"]["replies"][0])
    return response["llm"]["replies"][0]

# 启动 REST API 服务
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class QueryRequest(BaseModel):
    query: str

@app.post("/query")
def query_openai(request: QueryRequest):
    result = generate_answer(request.query)
    return result

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8089)