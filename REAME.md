# 📚 Implementações do Algoritmo K-Means em Java
Este projeto implementa o algoritmo de agrupamento K-Means, uma técnica popular de aprendizado não supervisionado usada para particionar dados em K grupos ou clusters. O K-Means é amplamente utilizado em várias aplicações de análise de dados e aprendizado de máquina devido à sua simplicidade e eficácia em lidar com grandes conjuntos de dados.

Neste projeto, aplicamos o K-Means em quatro conjuntos de dados distintos: Iris, Mfeat, Wine e Rice (Cammeo and Osmancik). Cada um desses conjuntos de dados oferece desafios e insights únicos, permitindo explorar a capacidade do algoritmo em diferentes contextos.

## 🔧 Requisitos e Instalação
Antes de executar o projeto, certifique-se de ter as seguintes ferramentas instaladas:

Java JDK 17 ou superior

Apache Maven

## 🌿 Estrutura do Projeto
O projeto é dividido em diferentes branches, cada uma com uma implementação distinta do algoritmo K-Means:

`basic-kmeans`: Implementa o K-Means tradicional usando a distância euclidiana para calcular a similaridade entre pontos.

`manhattan-kmeans`: Utiliza a medida de distância de Manhattan para calcular a similaridade, o que pode ser mais apropriado em certos conjuntos de dados onde as diferenças absolutas são mais relevantes.

`firefly-kmeans`: Aplica a meta-heurística Firefly ao algoritmo K-Means básico para melhorar a inicialização dos centróides e potencialmente encontrar melhores soluções globais.

## 🔍 Índices de Validação Utilizados

Para avaliar a eficácia do algoritmo de clustering e a qualidade dos clusters formados, utilizamos os seguintes índices de validação:

### 1. F-measure
- **Descrição:** O F-measure é uma métrica que combina precisão e recall para fornecer uma única pontuação de desempenho. É particularmente útil para avaliar a qualidade de clusters em relação a classes de referência conhecidas. O F-measure é calculado como a média harmônica da precisão (proporção de elementos corretamente identificados como parte de um cluster) e recall (proporção de elementos de uma classe que foram corretamente agrupados).

- **Aplicação:** É usado para medir a qualidade dos clusters em tarefas de classificação.

### 2. Adjusted Rand Index (ARI)
- **Descrição:** O ARI mede a similaridade entre dois agrupamentos, ajustando para o acaso. Um valor de ARI de 1 indica clusters idênticos, enquanto um valor próximo a 0 indica agrupamentos aleatórios. O ARI corrige a medida Rand Index (RI) pela chance de atribuição aleatória de objetos aos clusters.

- **Aplicação:** Avalia a qualidade dos clusters comparando-os com agrupamentos de referência conhecidos.

### 3. Davies-Bouldin Index (DBI)
- **Descrição:** O índice Davies-Bouldin mede a compacidade e separabilidade dos clusters. Valores menores indicam clusters bem separados e compactos. Este índice é calculado considerando a média da relação entre a distância dentro do cluster e a distância entre os clusters.

- **Aplicação:** Útil para avaliar a qualidade de agrupamentos não supervisionados sem classes de referência.

### 4. Calinski-Harabasz Index (CHI)
- **Descrição:** Também conhecido como Índice de Razão de Variância, o Calinski-Harabasz avalia a dispersão entre e dentro dos clusters. Valores mais altos indicam melhor qualidade do cluster. Ele é definido como a razão entre a soma da dispersão entre os clusters e a soma da dispersão dentro dos clusters.

- **Aplicação:** Este índice é usado para validar clusters em relação à dispersão interna e externa, favorecendo agrupamentos compactos e bem separados.
