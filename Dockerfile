# Use a imagem oficial do Node.js como base
FROM node:20

# Diretório de trabalho
WORKDIR /app

# Copia o package.json do backend
COPY backend/package*.json ./backend/

# Instala dependências
RUN cd backend && npm install

# Copia todo o backend
COPY backend ./backend

EXPOSE 9090

CMD ["node", "backend/server.js"]
