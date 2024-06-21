# FileServer

**Projeto realizado na disciplina de Sistemas Distribuídos, dentro do curso de Ciência da Computação, no ano de 2024.**

Servidor de Arquivos com Armazenamento Centralizado

Desenvolva um Sistema Distribuído composto por cliente e servidor para permitir a distribuição de arquivos. O armazenamento dos arquivos será centralizado no servidor, que deverá receber conexões de clientes que poderão: 
- Solicitar a listagem dos arquivos, 
- Solicitar um arquivo específico para download
- Enviar um arquivo. 

O servidor deverá manter o nome original do arquivo. Caso o nome de um novo arquivo enviado por um cliente já conste no servidor, o conteúdo do arquivo antigo será sobrescrito, mantendo os dados do novo arquivo. A estratégia de conexão poderá ser estática, onde o endereço e a porta podem ser definidos em código. Utilize o protocolo de transporte TCP e escolha a abordagem de definição de protocolos de aplicação que melhor lhe convir. A partir da abordagem escolhida, construa seu próprio protocolo de aplicação.

O cliente deverá interagir com o usuário por comandos de console, sendo eles:
- list: exibe uma lista de arquivos disponíveis no servidor. A lista deverá identificar cada arquivo por um número inteiro, contando a partir de 1.
- down COD: solicita o download de um arquivo do servidor, onde COD representa o valor numérico inteiro que identifica o arquivo.
- up caminho_para_o_arquivo: faz upload de um arquivo para o servidor, onde caminho_para_o_arquivo representa o caminho para o arquivo no cliente.
