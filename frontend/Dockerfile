FROM node:current-slim as build-stage

WORKDIR /frontend
# root 다음 Test을 지정 즉 호스트 디렉토리

COPY . .
# 앞의 점은 app밑의 모든 디렉토리 뒤의 점은 도커의 root directory

RUN npm install
RUN npm run build


CMD ["npm","start"]

FROM nginx:stable-alpine as production-stage

RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d/nginx.conf
COPY --from=build-stage ./frontend/dist /usr/share/nginx/html

EXPOSE 80 443

CMD ["nginx", "-g", "daemon off;"]


