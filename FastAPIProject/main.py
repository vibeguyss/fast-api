from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import requests
import json

app = FastAPI()

class PromptInput(BaseModel):
    prompt: str

@app.post("/chat")
def chat(prompt_input: PromptInput):
    try:
        prompt = "한국어로 대답해줘 "
        print(prompt + prompt_input.prompt,)
        response = requests.post(
            "http://localhost:11434/api/generate",
            headers={"Content-Type": "application/json"},
            data=json.dumps({
                "model": "llama3",
                "prompt": prompt + prompt_input.prompt,
                "stream": False  # 중요: 스트리밍 비활성화 (JSON 파싱 오류 방지)
            })
        )

        # 응답 상태코드 체크
        if response.status_code != 200:
            print("[Ollama 오류 응답]", response.text)
            raise HTTPException(status_code=500, detail="Ollama에서 오류 응답이 왔습니다.")

        # JSON 응답 파싱 시도
        try:
            result = response.json()
        except json.JSONDecodeError:
            print("[JSON 파싱 실패] 응답 내용:", response.text)
            raise HTTPException(status_code=500, detail="Ollama 응답을 JSON으로 파싱할 수 없습니다.")

        # 정상 응답 반환
        return {"response": result.get("response", "No response from model.")}

    except requests.exceptions.RequestException as e:
        print("[요청 실패]", str(e))
        raise HTTPException(status_code=500, detail="Ollama 서버에 연결할 수 없습니다.")