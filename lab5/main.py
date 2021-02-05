from tabu_search_algorithm import TabuSearchAlgorithm
from utilities import read_from_resources

if __name__ == "__main__":
    weights = read_from_resources("p01.15.291.tsp")
    print(weights)
    tabu_search_algorithm = TabuSearchAlgorithm(weights=weights)
    algorithm_result = tabu_search_algorithm.objective_function()
    print("\nAlgorithm result:", algorithm_result)

