# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
# Copy our project directory (locally) in the current directory of our docker image (/app)
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

COPY ./nginx/nginx.conf.template /etc/nginx/templates/default.conf.template

# Expose $PORT on container.
# We use a variable here as the port is something that can differ on the environment.
# By default, the nginx image listens to port 80. This should be set to the heroku defined port.
ENV LISTEN_PORT $PORT
EXPOSE $PORT

# Start the app
CMD [ "nginx", "-g", "daemon off;"]