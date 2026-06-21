# 🐦 Flappy Bird em Java

Clone do clássico Flappy Bird desenvolvido em Java utilizando Swing e programação orientada a objetos.

O projeto foi criado com o objetivo de praticar conceitos fundamentais de desenvolvimento de software, incluindo orientação a objetos, manipulação de eventos, renderização gráfica, lógica de jogos e estruturas de dados.

## 🎮 Demonstração

O jogador controla um pássaro que deve atravessar obstáculos sem colidir com os canos.

* Barra de espaço → Faz o pássaro pular
* Após Game Over → Pressione espaço para reiniciar

## ✨ Funcionalidades

* Sistema de gravidade
* Movimento do pássaro
* Geração aleatória de obstáculos
* Sistema de pontuação
* Detecção de colisões
* Reinício da partida
* Loop de jogo em tempo real
* Interface gráfica utilizando Java Swing

## 🛠 Tecnologias Utilizadas

* Java
* Java Swing
* AWT
* Programação Orientada a Objetos (POO)

## 📚 Conceitos Aplicados

### Orientação a Objetos

O projeto utiliza classes para representar os elementos do jogo:

* `App`
* `FlappyBird`
* `Bird`
* `Pipe`

### Eventos de Teclado

Captura da tecla espaço através da interface:

```java
KeyListener
```

### Game Loop

Atualização contínua da lógica do jogo utilizando:

```java
javax.swing.Timer
```

Executando aproximadamente 60 FPS.

### Detecção de Colisão

Implementação utilizando o algoritmo de colisão AABB (Axis-Aligned Bounding Box).

### Estruturas de Dados

Utilização de:

```java
ArrayList<Pipe>
```

para gerenciamento dinâmico dos obstáculos.

### Geração Aleatória

Criação de canos em posições aleatórias utilizando:

```java
Random
```

## 🚀 Como Executar

### Pré-requisitos

* Java JDK 17+ (ou versão compatível)

### Clonar o projeto

```bash
git clone https://github.com/dani-dantas/flappybird.git
```

### Entrar na pasta

```bash
cd flappybird
```

### Compilar

```bash
javac App.java FlappyBird.java
```

### Executar

```bash
java App
```

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido para fortalecer conhecimentos em:

* Java
* Programação Orientada a Objetos
* Desenvolvimento Desktop
* Estruturas de Dados
* Lógica de Programação
* Desenvolvimento de Jogos

## 🌱 Melhorias Futuras

* Sistema de recordes
* Efeitos sonoros
* Animação das asas do pássaro
* Diferentes níveis de dificuldade
* Menu inicial
* Persistência de pontuação
* Sistema de fases

## 👩‍💻 Autora

Daniella Dantas

📧 Contato profissional disponível no LinkedIn

🔗 GitHub: https://github.com/dani-dantas

💼 LinkedIn: https://www.linkedin.com/in/daniella-dantas

---

⭐ Se este projeto foi interessante para você, deixe uma estrela no repositório.
