# Tabu search algorithm

The implementation of the Tabu search algorithm for solving the Traveler Salesman Problem using different type of memories: short, middle and long ones.

### Algorithm running:

We runned algorithm for such a matrix `p01.15.291.tsp`:

```
[[ 0. 29. 82. 46. 68. 52. 72. 42. 51. 55. 29. 74. 23. 72. 46.]
 [29.  0. 55. 46. 42. 43. 43. 23. 23. 31. 41. 51. 11. 52. 21.]
 [82. 55.  0. 68. 46. 55. 23. 43. 41. 29. 79. 21. 64. 31. 51.]
 [46. 46. 68.  0. 82. 15. 72. 31. 62. 42. 21. 51. 51. 43. 64.]
 [68. 42. 46. 82.  0. 74. 23. 52. 21. 46. 82. 58. 46. 65. 23.]
 [52. 43. 55. 15. 74.  0. 61. 23. 55. 31. 33. 37. 51. 29. 59.]
 [72. 43. 23. 72. 23. 61.  0. 42. 23. 31. 77. 37. 51. 46. 33.]
 [42. 23. 43. 31. 52. 23. 42.  0. 33. 15. 37. 33. 33. 31. 37.]
 [51. 23. 41. 62. 21. 55. 23. 33.  0. 29. 62. 46. 29. 51. 11.]
 [55. 31. 29. 42. 46. 31. 31. 15. 29.  0. 51. 21. 41. 23. 37.]
 [29. 41. 79. 21. 82. 33. 77. 37. 62. 51.  0. 65. 42. 59. 61.]
 [74. 51. 21. 51. 58. 37. 37. 33. 46. 21. 65.  0. 61. 11. 55.]
 [23. 11. 64. 51. 46. 51. 51. 33. 29. 41. 42. 61.  0. 62. 23.]
 [72. 52. 31. 43. 65. 29. 46. 31. 51. 23. 59. 11. 62.  0. 59.]
 [46. 21. 51. 64. 23. 59. 33. 37. 11. 37. 61. 55. 23. 59.  0.]]
 ```
 
##### Runing properties
```
tabu_search_algorithm = TabuSearchAlgorithm(
      tabu_queue_size=5,  # the short memory queue size
      tabu_memory_strategy=MemoryStrategy.SAVE_IDS,  # the short memory strategy
      intensification_threshold_value=10, # the matrix value that should be reached to run intensification 
      diversification_threshold_value=15,  # the number of unsuccessive iterations that should be reached to run diversification
      diversification_pick_number=5,  # the diversification list size
      max_iterations=300,
)
```

### Algorithm results

```
Best value:  291.0
Best value frequency, %:  30.0
Mean:  315.4
Standard deviation:  22.33
Relative standard deviation, %:  7.08
```

#### Run-curve:
![run curve](https://github.com/scrubele/metaheuristic-algorithms/blob/main/tabu-search-algorithm/src/p01.15.291.tsp-run-history.png "Run curve")


#### Intensification matrix:
![Intensification](https://github.com/scrubele/metaheuristic-algorithms/blob/main/tabu-search-algorithm/src/p01.15.291.tsp-intensification.png "Intensification")


#### Diversification matrix:
![Diversification](https://github.com/scrubele/metaheuristic-algorithms/blob/main/tabu-search-algorithm/src/p01.15.291.tsp-diversification.png "Diversification")


#### Multiple run curve:
![Multiple run curve](https://github.com/scrubele/metaheuristic-algorithms/blob/main/tabu-search-algorithm/src/p01.15.291.tsp-multiple-run-history.png "Multiple Run curve")

#### Multiple run scatter plot:
![Multiple run scatter plot](https://github.com/scrubele/metaheuristic-algorithms/blob/main/tabu-search-algorithm/src/p01.15.291.tsp-multiple-run-scatter.png "Multiple run scatter plot")

