FROM node:16-slim as build
RUN mkdir -p /app
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY . /app/
RUN npm run build --prod

FROM nginx:1.21.4
COPY --from=build /app/dist/TwitterAppFront /usr/share/nginx/html
EXPOSE 4200:80

