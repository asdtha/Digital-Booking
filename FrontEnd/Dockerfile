FROM node:16.13.0-alpine 

WORKDIR /code

COPY package.json package.json
COPY package-lock.json package-lock.json

RUN npm install

COPY . . 

CMD ["npm", "run", "start"]