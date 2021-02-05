from strategies.memory_strategies import MemoryStrategy
from tabu_search_algorithm import TabuSearchAlgorithm
from utilities import read_from_resources, plot_matrix

DEBUG = False

if __name__ == "__main__":
    weights = read_from_resources("p01.15.291.tsp")
    print(weights)

    tabu_search_algorithm = TabuSearchAlgorithm(
        weights=weights,
        tabu_queue_size=5,
        tabu_memory_strategy=MemoryStrategy.SAVE_IDS,
        intensification_threshold_value=10,
        diversification_threshold_value=15,
        diversification_pick_number=5,
        max_iterations=200,
    )
    algorithm_result = tabu_search_algorithm.objective_function()

    intensification = tabu_search_algorithm.middle_term_memory
    print("intensification", intensification)
    plot_matrix(file_name="intensification.png", matrix=intensification.matrix)

    diversification = tabu_search_algorithm.long_term_memory
    print("diversification", diversification)
    plot_matrix(file_name="diversification.png", matrix=diversification.matrix)

    print("\nAlgorithm result:", algorithm_result)
