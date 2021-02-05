from strategies.memory_strategies import MemoryStrategy
from tabu_search_algorithm import TabuSearchAlgorithm
from utilities import read_from_resources, plot_matrix, plot_solutions

DEBUG = False


def run_algorithm():
    tabu_search_algorithm = TabuSearchAlgorithm(
        weights=weights,
        tabu_queue_size=5,
        tabu_memory_strategy=MemoryStrategy.SAVE_IDS,
        intensification_threshold_value=10,  # number of times, four top cities
        diversification_threshold_value=15,
        diversification_pick_number=5,
        max_iterations=300,
    )
    algorithm_result, plot_data = tabu_search_algorithm.objective_function()
    return tabu_search_algorithm, algorithm_result, plot_data


def run_multiple_items():
    i = 0
    smallest_values = []
    max_iterations = 10
    while i < max_iterations:
        tabu_search_algorithm, algorithm_result, plot_data = run_algorithm()
        smallest_values.append(algorithm_result.value)
        print("Algorithm result:", algorithm_result)
        i += 1
    smallest_value = min(smallest_values)
    smallest_value_frequency = sum([1 for value in smallest_values if value == smallest_value]) / max_iterations
    print("Smallest value frequency", smallest_value_frequency * 100, "%")


def run_algorithm_with_analytics():
    tabu_search_algorithm, algorithm_result, plot_data = run_algorithm()

    intensification = tabu_search_algorithm.middle_term_memory
    # print("intensification", intensification)
    plot_matrix(file_name="Intensification", matrix=intensification.matrix)

    diversification = tabu_search_algorithm.long_term_memory
    # print("diversification", diversification)
    plot_matrix(file_name="Diversification", matrix=diversification.matrix)

    print("\nAlgorithm result:", algorithm_result)

    plot_solutions(plot_data)


FILE_NAMES = ["p01.15.291.tsp", "five.19.tsp", "gr17.2085.tsp", "br17.39.atsp"]
if __name__ == "__main__":
    weights = read_from_resources(FILE_NAMES[0])
    # weights = read_from_resources("gr17.2085.tsp")
    print(weights)
    run_algorithm_with_analytics()
