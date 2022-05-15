## Princípio F.I.R.S.T
 - Fast: O teste deve ser rápido;
 - Independent: O teste deve ser independente;
 - Repeatable: O R vem de repetitível, a qualquer momento que for executado deve dar o mesmo resultado;
 - Self-Verifying: O próprio teste deve saber quando o resultado foi com sucesso, falha ou erro;
 - Timely: Este vem de oportuno. OU seja um teste deve ser criado no momento oportúnuo;

## Frameworks XUnit
 Estes frameworks tem este nome por conta no framework SUnit desenvolvido na linguagem de programação Smalltalk, criado por Kent Beck, 1998. Todos estes frameworks tentam seguir um mesmo componente principal de sua arquitetura, e são compostos de alguns componentes:
 - TestRunner: Responsável por executar os testes e coletar os resultados;
 - TestFixture (TestContext): Que são as precondições necessárias aos testes;
 - TestSuites: Permite que sejam agrupados um conjunto de testes que devem ser executados em conjunto;
 - TestResultFormatter: Quem formata a saída dos testes;
 - Assertions: São verificadores de estado do teste;

O JUnit também pode ser utilizado para testes de integração e testes funcionais como ferramenta de padronização.

## Metáfora da Janela de Vidro
Ela foi introduzida em 1982 na área de criminologia, e tenta explicar uma característica do comportamento humano. Considere um edifício com algumas janelas quebradas, se as janelas não forem reparadas, a tendencia é que vândalos quebrem mais janelas.
Portanto, é sugerido que sempre seja deixado os testes com a barra verde, pois assim, evitasse que mais testes com barra vermelha se acumulem.

## Aula 05 - Organização das classes
Colocar as classes de teste na mesma estrutura de pacote das classes testadas. Dessa forma, somente os atributos e métodos
privados não podem ser acessados mas os definidos com os outros modificadores são visíveis.

## Aula 06 - Assertivas
As assertivas são uma característica dos frameworks XUnit, ela está diretamente ligada a uma expressão lógica. Caso a
asserção falhe, o teste também falha.

## Aula 07 - AssertThat
O método **assertThat** da classe **Assert**, é usado para tornar as verificações de determinado caso de teste
mais flúidas, pois usa métodos encadeados que são conhecidos como matchers. Esse encadeamento
de métodos com nomes semânticos é conhecido como **Fluent Interface**, que foi mencionado primeiramente
por **Martin Fowler**, em 2005. O Junit tem a dependência para o **Hamcrest** que trás consigo diversos matchers úteis.
Vale notar que a ordem dos argumentos agora está invertida. No caso do método **assertThat**, o primeiro argumento
se refere ao valor atual, e o segundo argumento recebe um matcher com o valor esperado.
Para tornar a leitura da assertiva mais legível, basta adicionar imports estáticos dos métodos e matchers.

## Aula 08 - Divisão de um teste
Cada teste deve ser isolado, tendo na maioria das vezes somente uma única assertiva. Dessa forma, como os testes estariam
fazendo uma única verificação lógica, seria mais fácil fazer o rastreamento dos erros quando necessário.
Em caso de falha na asserção, o teste falha, conforme o esperado. Mesmo essa sendo uma forma de fazer a divisão de um teste
também há a outra maneira que é com mais asserções no mesmo teste. Essa situação é aceitável quando o cenário e ação
do teste não mudam com muita frequência, tornando assim o gerenciamento macro do teste e mais de uma asserção por teste.
Existe uma maneira de minimizar a abordagem macro dos testes que é coletando todos os erros de uma única vez, para posterior
análise em caso de falha em várias asserções de um teste. Essa funcionalidade é conhecida como **Rule**. Ela é apresentada
junto deste commit no teste **testeLocacao** da classe de teste **LocacaoServiceTest**. Essa duas soluções ou abordagens
são as mais usadas, e essa segunda, usando uma abordagem macro dos testes é preferível pelo autor do
curso, **Wagner Costa Aquino**.
