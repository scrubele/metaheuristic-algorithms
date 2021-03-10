from strategies.memory_strategies import MemoryStrategy
from tabu_search_algorithm import TabuSearchAlgorithm
from utilities import read_from_resources, plot_matrix, plot_solutions, find_statistic_properties

DEBUG = False


def run_algorithm():
    tabu_search_algorithm = TabuSearchAlgorithm(
        weights=weights,
        tabu_queue_size=11,  # the short memory queue size
        tabu_memory_strategy=MemoryStrategy.SAVE_IDS,  # the short memory strategy
        # the value that should be reached to run intensification (adding it to
        intensification_threshold_value=2,
        intensification_pick_number=11,
        # frozen list)
        # the value that should be reached to run diversification
        diversification_threshold_value=3,
        diversification_pick_number=8,  # the diversification list size
        max_iterations=599,
    )
    algorithm_result, plot_data = tabu_search_algorithm.objective_function()
    return tabu_search_algorithm, algorithm_result, plot_data


def run_analytics(tabu_search_algorithm, algorithm_result, plot_data):
    intensification = tabu_search_algorithm.middle_term_memory
    print("intensification", intensification)
    plot_matrix(file_name="Intensification", matrix=intensification.matrix)
    diversification = tabu_search_algorithm.long_term_memory
    print("diversification", diversification)
    plot_matrix(file_name="Diversification", matrix=diversification.matrix)
    plot_solutions(plot_data)


def run_algorithm_with_analytics():
    import time
    start_time = time.time()
    tabu_search_algorithm, algorithm_result, plot_data = run_algorithm()
    print("--- %s seconds ---" % (time.time() - start_time))
    run_analytics(tabu_search_algorithm, algorithm_result, plot_data)


def run_algorithm_multiple_times(max_iterations=5, best_value=33523):
    i = 0
    best_solutions = []
    while i < max_iterations:
        print(i)
        tabu_search_algorithm, algorithm_result, plot_data = run_algorithm()
        best_solutions.append(algorithm_result)
        print("Algorithm result:", algorithm_result)
        i += 1
    best_value, best_value_frequency, mean, standard_deviation, relative_deviation = find_statistic_properties(
        best_solutions, best_value=best_value)
    print("\nBest value: ", best_value)
    print("Best value frequency, %: ", best_value_frequency)
    print("Mean: ", mean)
    print("Standard deviation: ", standard_deviation)
    print("Relative standard deviation, %: ", relative_deviation)

    plot_solutions(best_solutions, file_name="multiple-run-history.png")
    # plot_scatter(best_solutions, file_name="multiple-run-scatter.png")


if __name__ == "__main__":
    file_names = ["att48.33523.tsp", "at42.699.tsp"]
    weights = read_from_resources(file_names[1])
    print(weights)
    run_algorithm_with_analytics()
    # run_algorithm_multiple_times()
