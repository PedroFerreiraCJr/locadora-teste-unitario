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
O método **AssertThat** da classe Assert, é usada para tornar as verificações de determinado caso de teste
mais flúidas, pois usa classes com métodos encadeados que são conhecidas como matchers. Esse encadeamento
de métodos com nomes semânticos é conhecido como Fluent Interface, que foi mencionado primeiramente
por **Martin Fowler**, em 2005. O Junit tem a dependência para o **Hamcrest** que trás consigo diversos matchers úteis.
Vale notar que a ordem dos argumentos agora está invertida. No caso do método **assertThat**, o primeiro argumento
se refere ao valor atual, e o segundo argumento recebe um matcher com o valor esperado.
Para tornar a leitura da assertiva mais legível, basta adicionar imports estáticos dos métodos e matchers.

