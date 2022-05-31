# rest_dabd
### Detalhes da matéria
- Centro Universitário UniCarioca
- Matéria: Desenvolvimento de Aplicações com Banco de Dados
- Período: 2022.1
- Atividade Prática Supervisionada 2

Uma API REST que se liga com um banco de dados contendo as entidades no MER abaixo.

### Esquema do Banco de Dados
#### Modelo Entidade Relacionamento
![Modelo Entidade Relacionamento](https://i.gyazo.com/34b0871b4eae9d2f3fe4ad5d24981b1e.png)
#### Modelo Relacional
![Modelo Relacional](https://i.gyazo.com/2d904511fe8c42bb1ec31ff276481612.png)

### Referência da API

| Método | Endpoint | Possíveis códigos | Descrição |
| ------ | -------- | ----------------- | --------- |
| GET | /doctor | 200 OK, 204 No Content | Retorna todos os médicos. |
| GET | /doctor/{crm} | 200 OK, 204 No Content | Retorna o médico com o CRM de cadastro especificado na URI. |
| POST | /doctor | 201 Created, 409 Conflict | Cria um novo médico, com o CRM, nome e especialidade informados. |
| PUT | /doctor/{crm} | 200 OK, 204 No Content, 409 Conflict | Edita as informações de um médico existente. Campos não informados ou com valor `null` não são alterados. |
| GET | /patient | 200 OK, 204 No Content | Retorna todos os pacientes. |
| GET | /patient/{cpf} | 200 OK, 204 No Content | Retorna o paciente com o CPF de cadastro especificado na URI. |
| POST | /patient | 201 Created, 409 Conflict | Cria um novo paciente, com o CPF, nome e endereço informados. |
| PUT | /patient/{cpf} | 200 OK, 204 No Content, 409 Conflict | Edita as informações de um paciente existente. Campos não informados ou com valor `null` não são alterados. |

### Instruções de instalação
- Requisitos básicos:
    - JRE/JDK com suporte a Java 8 no mínimo
    - Maven instalado

1. Faça o download desse repositório
2. Abra um shell (powershell, bash) e navegue até a pasta raiz
3. Inicie com o comando `mvn clean package`
4. Rode a aplicação com `mvn liberty:run`
5. Acesse a aplicação através do endereço http://localhost:9080
    - É recomendado o uso de uma ferramenta de síntese de requisições para testes (ex. [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/), [cURL](https://curl.se/))
