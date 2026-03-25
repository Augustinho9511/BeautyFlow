💇‍♀️ BeautyFlow API - Gestão Inteligente para Salões de Beleza
O BeautyFlow é uma API RESTful completa desenvolvida em Java e Spring Boot para automatizar a gestão de salões de beleza. O sistema vai muito além de um simples CRUD: ele integra agendamentos, controle de estoque inteligente, fluxo de caixa (lucro real) e cálculo automático de comissões.

🌍 Acesse a API em Produção (Swagger): Clique aqui para testar a API no Render

🚀 Tecnologias Utilizadas
Linguagem: Java 17

Framework: Spring Boot 3

Banco de Dados: PostgreSQL (Nuvem)

Segurança: Spring Security + Token JWT

Migrações/ORM: Hibernate / Spring Data JPA

Documentação: Swagger (Springdoc OpenAPI)

Deploy: Docker + Render

🧠 Diferenciais e Regras de Negócio Implementadas
O grande diferencial desta API é a comunicação entre os domínios. As ações geram reações em cadeia no sistema:

📦 Baixa Automática de Estoque: Ao concluir um agendamento, o sistema lê a Ficha Técnica do serviço prestado e deduz automaticamente a quantidade exata de produto gasto do estoque.

🔄 Estorno Inteligente: Se um agendamento for cancelado, os produtos são devolvidos ao estoque automaticamente.

🚨 Alerta de Estoque Crítico: Rota exclusiva que monitora e lista apenas produtos que atingiram a quantidade mínima configurada, auxiliando nas compras do salão.

💸 Cálculo de Lucro Real: O sistema não considera apenas o preço cobrado pelo serviço, mas desconta o custo exato dos produtos utilizados por medida para dar o lucro real ao salão.

💰 Folha de Comissões Automática: Gera relatórios calculando o valor exato a ser pago para cada profissional no fim do mês baseado nos serviços concluídos.

🛡️ Validações de Domínio: Bloqueio de agendamentos no passado, proteção contra conflito de horários e impedimento de agendamentos com clientes ou profissionais inativos.

⚙️ Estrutura da API (Principais Funcionalidades)
A API é protegida por autenticação JWT (exceto a rota de login).

👥 Gestão de Pessoas
POST /clientes - Cadastra novos clientes.

POST /profissionais - Cadastra profissionais com suas especialidades e % de comissão.

As exclusões são lógicas (inativação) para não quebrar o histórico de agendamentos.

✂️ Serviços e Produtos
POST /servicos - Registra serviços (tempo estimado e valor).

POST /produtos - Registra produtos (calcula automaticamente o custo por medida).

GET /produtos/estoque-critico - Retorna produtos abaixo do nível mínimo.

POST /fichas-tecnicas - Associa um produto a um serviço com a quantidade gasta.

📅 Agendamentos
POST /agendamentos - Cria um agendamento validando disponibilidade.

PATCH /agendamentos/{id}/concluir - Conclui o serviço e movimenta o estoque.

PATCH /agendamentos/{id}/cancelar - Cancela e faz o estorno do estoque.

📊 Financeiro (Dashboard)
GET /agendamentos/resumo - Traz o faturamento bruto, lucro líquido e total de agendamentos.

GET /agendamentos/comissoes - Calcula a comissão de cada profissional baseada nos serviços concluídos.

💻 Como rodar o projeto localmente
Clone este repositório:

Bash
git clone https://github.com/Augustinho9511/BeautyFlow.git
Configure o seu banco de dados PostgreSQL e adicione as variáveis de ambiente no seu sistema ou na sua IDE:

Properties
DB_URL=jdbc:postgresql://localhost:5432/beautyflow
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_senha_secreta_jwt
Rode a aplicação utilizando a sua IDE ou via terminal com o Gradle:

Bash
./gradlew bootRun
Acesse o Swagger localmente em: http://localhost:8080/swagger-ui/index.html

Projeto desenvolvido por Pedro Augustinho. Conecte-se comigo no LinkedIn.
