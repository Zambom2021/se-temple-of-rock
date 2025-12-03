# Temple Of Rock - Testes Automatizados com Selenium + Java

Projeto de testes automatizados para a aplicação Temple Of Rock, migrado de Cypress para Selenium WebDriver com Java.

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- Google Chrome instalado
- Aplicação Temple Of Rock rodando em `http://localhost:9090`

## 🏗️ Estrutura do Projeto

```
src/
├── main/java/br/com/templeofrock/
│   ├── models/
│   │   ├── User.java
│   │   ├── Band.java
│   │   └── Disc.java
│   ├── pages/
│   │   ├── BasePage.java
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── OptionsPage.java
│   │   ├── RegisterBandPage.java
│   │   └── EditBandPage.java
│   └── utils/
│       ├── FakerUtils.java
│       └── ApiHelper.java
└── test/java/br/com/templeofrock/tests/
    ├── BaseTest.java
    └── TempleOfRockTest.java
```

## 🔧 Instalação

1. Clone o repositório
2. Instale as dependências:

```bash
mvn clean install
```

## ▶️ Executando os Testes

### Executar todos os testes:
```bash
mvn test
```

### Executar um teste específico:
```bash
mvn test -Dtest=TempleOfRockTest#verificaTituloPagina
```

### Executar com relatório Allure:
```bash
mvn clean test
mvn allure:serve
```

## 📊 Relatórios

O projeto está configurado com Allure Report para geração de relatórios detalhados:

- **Epic**: Temple Of Rock
- **Feature**: Funcionalidades principais do sistema
- **Severidade**: Classificação de criticidade dos testes

## 🧪 Testes Implementados

| Teste | Descrição |
|-------|-----------|
| `verificaTituloPagina` | Valida o título da página |
| `consultaListaBandasPorLetra` | Busca bandas por letra (d, i, l, u) |
| `consultaBandaPorNome` | Busca bandas específicas |
| `fazLoginComSucesso` | Login com credenciais válidas |
| `fazLoginComFalha` | Login com credenciais inválidas |
| `cadastraNovoUsuarioComSucesso` | Registro de novo usuário |
| `cadastraUsuarioComEmailInvalido` | Validação de e-mail inválido |
| `cadastraNovaBanda` | Cadastro de nova banda |
| `editaBandaEIncluiDiscos` | Edição de banda e adição de discos |
| `incluiDiscosParaBanda` | Adição de discos à discografia |
| `desisteDaEdicaoDeBanda` | Cancelamento de edição |

## 📦 Dependências Principais

- **Selenium WebDriver 4.20.0** - Automação web
- **JUnit 5.10.2** - Framework de testes
- **WebDriverManager 5.8.0** - Gerenciamento automático de drivers
- **JavaFaker 1.0.2** - Geração de dados fake
- **RestAssured 5.4.0** - Testes de API
- **Allure 2.25.0** - Relatórios

## 🎯 Padrões Utilizados

- **Page Object Model (POM)** - Separação de lógica de página e testes
- **AAA Pattern** - Arrange, Act, Assert
- **Factory Pattern** - Geração de dados com FakerUtils
- **DRY Principle** - Reutilização de código através de Page Objects

## 🔍 Funcionalidades Especiais

### FakerUtils
Classe utilitária para geração de dados aleatórios:
- Membros de banda
- Discografia
- Usuários
- Gêneros musicais

### ApiHelper
Helper para chamadas de API REST:
- Buscar ID de banda por nome
- Obter ano de formação

### BasePage
Classe base com métodos reutilizáveis:
- Esperas explícitas
- Scroll automático
- Manipulação de elementos

## 📝 Notas

- Os testes aguardam 5 segundos após login bem-sucedido (similar ao Cypress)
- Exceções não tratadas são ignoradas (similar à configuração Cypress)
- WebDriver é gerenciado automaticamente pelo WebDriverManager
- Browser é fechado automaticamente após cada teste

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto é de código aberto e está disponível sob a licença MIT.