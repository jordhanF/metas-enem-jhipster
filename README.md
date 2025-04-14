# 📈 Desafio: Cadastro de Metas ENEM com JHipster

## 🌐 Sobre o Projeto

Aplicativo completo gerado com **JHipster** para cadastro de **metas de notas do ENEM** para alunos.

Cada aluno pode ter metas nas seguintes 4 áreas do ENEM:

- Linguagens, Códigos e suas Tecnologias
- Ciências Humanas e suas Tecnologias
- Ciências da Natureza e suas Tecnologias
- Matemática e suas Tecnologias

Apenas um **usuário administrador** tem acesso à plataforma, onde pode:

1. Acessar o sistema com o login **admin/admin**
2. Cadastrar novos alunos
3. Definir metas de nota para cada aluno

---

## ⚡ Tecnologias Utilizadas

- **Backend:** Java (Spring Boot)
- **Frontend:** Angular
- **Banco de Dados:** PostgreSQL
- **Ferramenta de Geração:** JHipster
- **Gerenciamento de Containers:** Docker & Docker Compose

---

## 🔧 Como Rodar o Projeto Localmente

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/metas-enem.git
cd metas-enem
```

### 2. Suba o ambiente com Docker

Certifique-se de que o **Docker Desktop** esteja instalado e rodando.

```bash
docker-compose up -d
```

Isso iniciará o banco de dados PostgreSQL.

### 3. Rode a aplicação

Se estiver usando o JHipster com o backend:

```bash
./mvnw
```

E para o frontend:

```bash
cd src/main/webapp
npm start
```

### 4. Acesse a aplicação

Abra o navegador em:

```
http://localhost:8080
```

Login: `admin` Senha: `admin`

---

## 📚 Passo a Passo do Desenvolvimento

1. **Modelagem com JDL**

   - Utilizei o [JDL Studio](https://start.jhipster.tech/jdl-studio/) para modelar as entidades `Aluno` e `Meta`
   - Gerei o código com o comando `jhipster import-jdl modelo.jdl`

2. **Geração da aplicação**

   - A aplicação completa (backend + frontend) foi gerada automaticamente pelo JHipster.

3. **Configuração do banco de dados**

   - Definido PostgreSQL para os ambientes de desenvolvimento e produção
   - Utilizado Docker para facilitar o setup do banco de dados

4. **Testes e ajustes**

   - Realizei login, criei um aluno chamado "Lucas" e defini uma meta de 800 pontos, conforme o enunciado.
   - Adicionei melhorias de usabilidade e realizei pequenos ajustes visuais no frontend.

---

## 🚀 Benefícios do Docker neste Projeto

A utilização do Docker trouxe diversas vantagens:

### ✅ Ambiente padronizado

Todos os desenvolvedores e ambientes rodam com a mesma versão de PostgreSQL, sem conflito de dependências locais.

### ✅ Rápida configuração do banco

Com um simples `docker-compose up`, o banco já está configurado e rodando, economizando tempo de instalação.

### ✅ Portabilidade

A aplicação pode ser facilmente executada em qualquer máquina com Docker, o que é ideal para deploy em nuvem ou servidores.

### ✅ Isolamento e segurança

Os serviços rodam isolados em containers, evitando conflitos com outros projetos ou serviços locais.

---

## 🔗 Link do Repositório

[https://github.com/seu-usuario/metas-enem](https://github.com/seu-usuario/metas-enem)

---

## 📹 Demonstração em Vídeo

[Link do vídeo de demonstração](https://www.youtube.com/...) _(substituir com o link real)_

---

## 💪 Diferenciais Aplicados

- Melhorias na interface visual gerada
- Validações de campos e ajustes no layout
- Uso completo de Docker e Docker Compose
- Estrutura de código limpa, versionada no GitHub
