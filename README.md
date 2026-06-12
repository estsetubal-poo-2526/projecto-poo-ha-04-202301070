# Dungeon Escape

Jogo de exploração de masmorras desenvolvido em Java com JavaFX.

## Requisitos

- **Java JDK 22** (ou superior) — https://www.oracle.com/java/technologies/downloads/
- **Maven** — necessário para compilar e executar o projeto (instruções de instalação abaixo)
- **IntelliJ IDEA** (recomendado, especialmente para evitar a instalação manual do Maven)

## 1. Obter o projeto

```bash
git clone https://github.com/<utilizador>/<repositorio>.git
cd <repositorio>
```

Alternativa: descarregar o ZIP do GitHub (botão verde **Code > Download ZIP**) e extrair a pasta.

## 2. Como executar

### Opção A — IntelliJ IDEA (mais simples, sem instalação manual do Maven)

1. Abrir o IntelliJ IDEA.
2. **File > Open** e selecionar a pasta do projeto (a que contém o `pom.xml`).
3. O IntelliJ deteta o `pom.xml` e descarrega automaticamente as dependências (Maven embutido).
4. Na barra lateral direita, abrir o painel **Maven > Plugins > javafx > javafx:run** e fazer duplo clique.
   - Alternativa: abrir `src/main/java/org/example/Main.java` e clicar no botão ▶️ (Run) ao lado da classe.

### Opção B — Linha de comandos com Maven

#### Instalar o Maven

- **Windows**:
  ```
  winget install Apache.Maven
  ```
  ou descarregar de https://maven.apache.org/download.cgi, extrair e adicionar a pasta `bin` à variável de ambiente `PATH`.
- **macOS**:
  ```
  brew install maven
  ```
- **Linux (Debian/Ubuntu)**:
  ```
  sudo apt install maven
  ```

Confirmar a instalação com:
```bash
mvn -v
```

#### Executar o jogo

```bash
mvn clean javafx:run
```

Em caso de erro relacionado com a plataforma do JavaFX, editar o `pom.xml` e ajustar a propriedade `javafx.platform` conforme o sistema operativo:

| Sistema | Valor |
|---|---|
| Windows | `win` |
| Linux | `linux` |
| macOS (Intel) | `mac` |
| macOS (Apple Silicon) | `mac-aarch64` |

## Como jogar

- **Movimento do jogador**: setas direcionais ou `W`, `A`, `S`, `D`
- **Uso de item do inventário**: clicar no botão correspondente ("Usar ...")
- **Objetivo**: explorar as salas, recolher itens, derrotar inimigos/boss e encontrar a saída para vencer

### Legenda

| Símbolo | Significado |
|---|---|
| Jogador | Símbolo do jogador (posição atual) |
| `X` | Saída bloqueada (precisa de chave) |
| `S` | Saída desbloqueada |
| Itens | Poções de vida/ataque, chaves, etc. |
| Inimigos | Inimigos normais, rápidos e boss |

## Executar os testes

```bash
mvn test
```

## Estrutura do projeto

```
src/main/java/org/example/   # Código-fonte do jogo (Game, Player, Room, Enemy, Items, etc.)
src/test/java/org/example/   # Testes unitários
pom.xml                       # Configuração do Maven
```
