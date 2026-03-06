# Conversor de Moedas

Uma aplicação Java simples e eficiente para conversão de moedas em tempo real, utilizando a API ExchangeRate-API.

## 📋 Descrição

O **Conversor de Moedas** é uma ferramenta que permite ao usuário converter valores entre diferentes moedas internacionais. A aplicação obtém as taxas de câmbio atualizadas em tempo real por meio da API ExchangeRate-API, oferecendo conversões precisas e confiáveis.

## ✨ Características

- ✅ Conversão de moedas em tempo real
- ✅ Suporte a múltiplas moedas internacionais
- ✅ Interface de linha de comando intuitiva
- ✅ Tratamento robusto de exceções
- ✅ Desserialização automática de JSON com Gson
- ✅ Código organizado e escalável

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida:

```
src/main/java/com/fichel/conversordemoedas/
├── ConversorDeMoedasApplication.java   (Classe principal - orquestrador)
└── ObjetoConv.java                     (Model - representação dos dados da API)
```

### Componentes

#### **ConversorDeMoedasApplication.java**
- Classe principal da aplicação
- Gerencia a interação com o usuário através de um loop infinito
- Realiza requisições HTTP para a API de câmbio
- Processa a entrada do usuário e exibe resultados

#### **ObjetoConv.java**
- Classe modelo (POJO) que representa a resposta da API
- Desserializa automaticamente o JSON recebido
- Contém o método `converterPara()` para realizar conversões

## 🚀 Como Usar

### Pré-requisitos

- Java 11 ou superior
- Maven 3.6 ou superior
- Conexão com a internet (para acessar a API)

### Instalação

1. Clone ou faça download do projeto:
```bash
git clone <url-do-repositorio>
cd ConversorDeMoedas
```

2. Compile o projeto:
```bash
mvn clean compile
```

3. Execute a aplicação:
```bash
mvn exec:java -Dexec.mainClass="com.fichel.conversordemoedas.ConversorDeMoedasApplication"
```

Ou compile e execute via JAR:
```bash
mvn clean package
java -jar target/ConversorDeMoedas-1.0.jar
```

### Fluxo de Uso

1. **Inicie a aplicação** - Você verá a mensagem de boas-vindas
2. **Digite a moeda de origem** - Ex: `USD`, `EUR`, `BRL`
3. **Digite a moeda de destino** - Ex: `EUR`, `BRL`, `AUD`
4. **Digite o valor** - O valor a ser convertido
5. **Visualize o resultado** - A conversão será exibida na tela
6. **Opção de repetir** - Escolha `1` para outra conversão ou `0` para sair

### Exemplo de Execução

```
Bem-vindo ao Conversor de Moedas!
---------------------------------
 
Digite a moeda de origem (ex: USD): 
USD
Digite a moeda de destino (ex: EUR): 
BRL
Digite o valor a ser convertido: 
100
100.00 USD equivalem a 487.45 BRL
Deseja realizar outra conversão? (1 - Sim,  0 - Não)
1
```

## 📚 Dependências

As seguintes dependências estão configuradas no `pom.xml`:

### Gson
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.9</version>
</dependency>
```
- Responsável pela desserialização automática do JSON retornado pela API

### Java HTTP Client
- Nativa do Java 11+
- Utilizada para fazer requisições HTTP sem dependências externas

## 🔧 Detalhes Técnicos

### API Utilizada

**ExchangeRate-API**
- **Endpoint**: `https://v6.exchangerate-api.com/v6/{API_KEY}/latest/{MOEDA}`
- **Método**: GET
- **Resposta**: JSON com estrutura de map de conversão

### Estrutura da Resposta da API

```json
{
  "base_code": "USD",
  "conversion_rates": {
    "USD": 1,
    "AUD": 1.4817,
    "BGN": 1.7741,
    "BRL": 4.8745,
    "EUR": 0.9205,
    ...
  }
}
```

### Método de Conversão

A classe `ObjetoConv` possui o método:

```java
public double converterPara(String currencyCode, double valor)
```

**Funcionamento:**
1. Busca a taxa de câmbio para a moeda de destino no mapa `conversion_rates`
2. Multiplica o valor informado pela taxa encontrada
3. Retorna o resultado da conversão

**Validação:**
- Se a moeda não existir, lança uma `IllegalArgumentException`

## ⚠️ Tratamento de Exceções

A aplicação trata as seguintes exceções:

| Exceção | Causa | Ação |
|---------|-------|------|
| `IOException` | Erro de conexão com a API | Lança `RuntimeException` |
| `InterruptedException` | Thread interrompida | Lança `RuntimeException` |
| `IllegalArgumentException` | Moeda não encontrada | Mensagem de erro ao usuário |

Todas as exceções de rede são capturadas e relançadas como `RuntimeException` com mensagem descritiva.

## 📝 Fluxo da Aplicação

```
┌─────────────────────────────────┐
│   Iniciar Aplicação             │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Exibir Menu de Boas-vindas    │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Solicitar Moeda de Origem     │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Fazer Requisição à API        │
│   (com moeda concatenada)       │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Desserializar JSON → ObjetoConv
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Solicitar Moeda de Destino    │
│   e Valor a Converter           │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Realizar Conversão            │
│   (converterPara)               │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Exibir Resultado              │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   Perguntar por Nova Conversão  │
└────────────┬────────────────────┘
             │
       Sim ──┴── Não
       │           │
       └──┐      ┌─┘
          │      │
          │      ▼
          │   ┌──────────────┐
          │   │   Encerrar   │
          │   └──────────────┘
          │
          └─→ Loop (voltar ao início)
```

## 📄 Licença

Este projeto é de código aberto e pode ser usado livremente.


