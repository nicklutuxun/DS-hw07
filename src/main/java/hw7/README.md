# Discussion

For `OpenAddressingHashMap`, I use Linear Probing strategy and rehash using table size of primes: `{2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977}`. Rehash will occur when the load factor is sufficiently large, and the choice of this
threshold would affect the overall performance of HashMap, which well be discussed afterwards.

For `ChainingHashMap`, I use singly linked list as the auxiliary data structure. Rehash strategy is the same as that of `OpenAddressingHashMap`. 

- load factor=0.95, original primes set

When the load factor is large, the rehashing process is occurred less frequently. `OpenAddressingHashMap` tends to be slower, while the speed of `ChainingHashMap` is relatively
unchanged. Also, `OpenAddressingHashMap` uses less space, while `ChainingHashMap` uses more space.

| Benchmark         |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|-------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| buildSearchEngine |    apache.txt | avgt | 2   |            241.823            |         173.205         | ms/op |
| buildSearchEngine |       jhu.txt | avgt | 2   |             0.248             |          0.236          | ms/op |
| buildSearchEngine |    joanne.txt | avgt | 2   |             0.104             |          0.085          | ms/op |
| buildSearchEngine |    newegg.txt | avgt | 2   |            107.501            |         88.883          | ms/op |
| buildSearchEngine | random164.txt | avgt | 2   |            881.179            |         672.077         | ms/op |
| buildSearchEngine |      urls.txt | avgt | 2   |             0.029             |          0.029          | ms/op |


| Benchmark          |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|--------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| maximumUsedAfterGc |    apache.txt | avgt | 2   |         119939252.000         |      152616748.000      | bytes |
| maximumUsedAfterGc |       jhu.txt | avgt | 2   |         24259788.000          |      24383664.000       | bytes |
| maximumUsedAfterGc |    joanne.txt | avgt | 2   |         23867152.000          |      23833308.000       | bytes |
| maximumUsedAfterGc |    newegg.txt | avgt | 2   |         70079804.000          |      101138264.000      | bytes |
| maximumUsedAfterGc | random164.txt | avgt | 2   |        1682645204.000         |     1891132408.000      | bytes |
| maximumUsedAfterGc |      urls.txt | avgt | 2   |         23446500.000          |      23327920.000       | bytes |

- load factor=0.75, original primes set

`ChainingHashMap` is generally faster than `OpenAddressingHashMap`, but generally it needs more space.

| Benchmark         |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|-------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| buildSearchEngine |    apache.txt | avgt | 2   |            193.984            |         173.215         | ms/op |
| buildSearchEngine |       jhu.txt | avgt | 2   |             0.248             |          0.213          | ms/op |
| buildSearchEngine |    joanne.txt | avgt | 2   |             0.103             |          0.088          | ms/op |
| buildSearchEngine |    newegg.txt | avgt | 2   |            102.442            |         88.785          | ms/op |
| buildSearchEngine | random164.txt | avgt | 2   |            827.489            |         642.857         | ms/op |
| buildSearchEngine |      urls.txt | avgt | 2   |             0.029             |          0.029          | ms/op |


| Benchmark          |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|--------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| maximumUsedAfterGc |    apache.txt | avgt | 2   |         169431940.000         |      119565296.000      | bytes |
| maximumUsedAfterGc |       jhu.txt | avgt | 2   |         24237272.000          |      24424108.000       | bytes |
| maximumUsedAfterGc |    joanne.txt | avgt | 2   |         24017904.000          |      24278236.000       | bytes |
| maximumUsedAfterGc |    newegg.txt | avgt | 2   |         102011736.000         |      68838000.000       | bytes |
| maximumUsedAfterGc | random164.txt | avgt | 2   |        1780213484.000         |     1881624324.000      | bytes |
| maximumUsedAfterGc |      urls.txt | avgt | 2   |         23321396.000          |      23176472.000       | bytes |

- load factor=0.4, original primes set

As the load factor decrease, the threshold for rehashing is lowered, which means now it is more likely and frequently a rehashing could occur. `OpenAddressingHashMap` tends
to be a bit faster. But `ChainingHashMap` generally is slower, which I think is because of the relative costly insert in rehashing process.

| Benchmark         |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|-------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| buildSearchEngine |    apache.txt | avgt | 2   |            183.168            |         177.388         | ms/op |
| buildSearchEngine |       jhu.txt | avgt | 2   |             0.217             |          0.219          | ms/op |
| buildSearchEngine |    joanne.txt | avgt | 2   |             0.091             |          0.091          | ms/op |
| buildSearchEngine |    newegg.txt | avgt | 2   |            86.979             |         89.954          | ms/op |
| buildSearchEngine | random164.txt | avgt | 2   |            684.682            |         634.605         | ms/op |
| buildSearchEngine |      urls.txt | avgt | 2   |             0.029             |          0.030          | ms/op |


| Benchmark          |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|--------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| maximumUsedAfterGc |    apache.txt | avgt | 2   |         121310148.000         |      112539704.000      | bytes |
| maximumUsedAfterGc |       jhu.txt | avgt | 2   |         23897784.000          |      24417096.000       | bytes |
| maximumUsedAfterGc |    joanne.txt | avgt | 2   |         23840928.000          |      23969456.000       | bytes |
| maximumUsedAfterGc |    newegg.txt | avgt | 2   |         78462308.000          |      62855532.000       | bytes |
| maximumUsedAfterGc | random164.txt | avgt | 2   |        1860749620.000         |     1838179108.000      | bytes |
| maximumUsedAfterGc |      urls.txt | avgt | 2   |         23383488.000          |      23264572.000       | bytes |

- load factor=0.4, more concentrated primes set `{2, 3, 5, 7, 11, 17, 23, 37, 53, 79, 127, 173, 263, 397, 587, 877, 1319, 1973, 2957, 4441, 6653, 10007, 14969, 22447, 33679, 50503, 75767, 113647, 170447, 255679, 383519, 575257, 862879, 1294339, 1941479, 2912227, 4368341, 6552503, 9828761 , 14743129}`, this set
is approximately 1.5 times apart, instead of 2 times apart in the original set. A more concentrated set means the number of rehashing would increase.

Both `OpenAddressingHashMap` and `ChainingHashMap` tend to be slower, space used is relatively unchanged.

| Benchmark         |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|-------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| buildSearchEngine |    apache.txt | avgt | 2   |            198.364            |         188.906         | ms/op |
| buildSearchEngine |       jhu.txt | avgt | 2   |             0.233             |          0.222          | ms/op |
| buildSearchEngine |    joanne.txt | avgt | 2   |             0.098             |          0.093          | ms/op |
| buildSearchEngine |    newegg.txt | avgt | 2   |            103.522            |         91.253          | ms/op |
| buildSearchEngine | random164.txt | avgt | 2   |           1011.266            |         759.784         | ms/op |
| buildSearchEngine |      urls.txt | avgt | 2   |             0.029             |          0.029          | ms/op |


| Benchmark          |    (fileName) | Mode | Cnt | `OpenAddressingHashMap` score | `ChainingHashMap` score | Units |
|--------------------|--------------:|------|-----|:-----------------------------:|:-----------------------:|-------|
| maximumUsedAfterGc |    apache.txt | avgt | 2   |         120026556.000         |      113456696.000      | bytes |
| maximumUsedAfterGc |       jhu.txt | avgt | 2   |         24287640.000          |      24452460.000       | bytes |
| maximumUsedAfterGc |    joanne.txt | avgt | 2   |         23900844.000          |      24147912.000       | bytes |
| maximumUsedAfterGc |    newegg.txt | avgt | 2   |         70246272.000          |      76171448.000       | bytes |
| maximumUsedAfterGc | random164.txt | avgt | 2   |        1895549484.000         |     1824289052.000      | bytes |
| maximumUsedAfterGc |      urls.txt | avgt | 2   |         23323936.000          |      23282064.000       | bytes |

- In the above tests, for running time I find: when the load factor decreases, `OpenAddressingHashMap` tends to be faster and the change is not linear, while the speed of `ChainingHashMap`
is generally unchanged and seems to be affected mostly by the datasets; when primes set is more concentrated, both `OpenAddressingHashMap` and `ChainingHashMap` tend to be slower.
- For space used, I find: when the load factor decreases, `OpenAddressingHashMap` generally uses more space, while space used by `ChainingHashMap` is relatively unchanged. 
- In general, the results follow my expectations: a lower load factor generally decrease the chance of collision, make speed faster and space less; a concentrated primes set increase the frequency of rehashing, make speed slower.
- But a few results are surprising: 1. for dataset newegg.txt the space used by `ChainingHashMap` is significantly less than `OpenAddressingHashMap`,
which I think is due to some unique property of this dataset; 2. The correlation of load factor and space is not very significant as there are some cases it behaves the other way around.
- I choose my final HashMap implementation to be `ChainingHashMap` when load factor is around 0.4 with a more scattered primes set.
