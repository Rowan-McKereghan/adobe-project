FROM node:23-alpine3.20 AS build
WORKDIR /react
COPY package*.json ./
COPY index.html ./
COPY vite.config.js ./
RUN npm install
COPY src ./src
COPY public ./public
RUN npm run build

FROM node:23-alpine3.20
WORKDIR /react-frontend-build
RUN npm install -g serve
COPY --from=build /react/dist ./build
EXPOSE 3000
CMD ["serve", "-s", "build"]