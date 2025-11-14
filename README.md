Projeto Sistema de Vendas SaaS

Visão Geral

Este projeto é um sistema SaaS para gestão de estoque e vendas, com suporte multiempresa (multitenant) e controle de usuários por empresa.

Configuração do Ambiente

Pré-requisitos

JDK 17+ instalado

Docker e Docker Compose (opcional, para banco MySQL local)

IntelliJ IDEA, VSCode ou outro IDE de sua preferência

Banco MySQL rodando (local ou remoto)

Git instalado

Clonar o projeto


bash
git clone <URL do seu repositório>
cd seu-projeto

Configurar .env

Na raiz do projeto, crie o arquivo .env com as variáveis:


text
DB_URL=jdbc:mysql://localhost:3306/nomedobanco?useSSL=false&serverTimezone=UTC
DB_USER=seu_usuario
DB_PASSWORD=sua_senha

Importante:

O arquivo .env está no .gitignore, não deve ser versionado.

Cada membro da equipe deve criar seu próprio .env local.

Executar aplicação

No IDE ou terminal:


bash
./mvnw spring-boot:run

Ou via Docker se configurado.

Estrutura do Projeto

model: Entidades JPA com Lombok

repository: Interfaces Spring Data JPA

service: Lógica de negócio com validações e regras de negócio

controller: Endpoints REST expostos para frontend e clientes

exception: Classes para tratamento de erros customizados

Fluxo de Trabalho

Usar branches para funcionalidades específicas (feature/xyz)

Fazer commits curtos e claros

Abrir pull requests para revisão do time

Sincronizar frequentemente com o branch principal (main ou develop)

Próximos Passos

Configurar autenticação com Spring Security e JWT

Desenvolver frontend SPA consumindo a API

Implementar testes automatizados para garantir qualidade

Comunicação

Canal oficial do projeto: Discord

Revisões semanais para acompanhamento