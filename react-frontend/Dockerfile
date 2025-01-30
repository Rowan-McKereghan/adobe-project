FROM node:23-alpine3.20 AS build
WORKDIR /react
COPY package*.json ./
RUN npm install
COPY src ./src
COPY public ./public
RUN npm run build

FROM node:23-alpine3.20
WORKDIR /react-frontend-build
RUN npm install -g serve
COPY --from=build /react/build ./build
EXPOSE 3000
CMD ["serve", "-s", "build", "-l", "3000"]