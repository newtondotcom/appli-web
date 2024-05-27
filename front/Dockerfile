# Use the official Node.js image as the base image
FROM node:20-alpine

# Install pnpm
RUN npm install -g pnpm

# Set the working directory
WORKDIR /app

# Copy package.json and pnpm-lock.yaml
COPY package.json pnpm-lock.yaml ./

# Install dependencies
RUN pnpm i

# Copy the rest of the application code
COPY . .

# Build the Nuxt.js application
RUN pnpm run build

# Expose the port the app runs on
EXPOSE 3000

# Start the Nitro server
CMD ["node",".output/server/index.mjs"]
