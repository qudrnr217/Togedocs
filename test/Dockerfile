FROM node:current-slim


RUN mkdir -p /test

WORKDIR /test
# root 다음 Test을 지정 즉 호스트 디렉토리

COPY . .
# 앞의 점은 app밑의 모든 디렉토리 뒤의 점은 도커의 root directory

RUN npm install

EXPOSE 8001

CMD ["npm","start"]
