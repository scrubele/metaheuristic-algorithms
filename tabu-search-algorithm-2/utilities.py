import collections
import statistics

import matplotlib.pyplot as plt
import numpy as np

RESULT_FOLDER = "results/"


def read_from_resources(file_name: str) -> np.array:
    input = np.loadtxt("resources/" + file_name)
    return input


def plot_matrix(file_name, matrix):
    # print('hey')
    fig, ax = plt.subplots(1, 1, figsize=(15, 15))
    # print()
    ax.imshow(matrix, cmap='viridis', interpolation='nearest')
    # Loop over data dimensions and create text annotations.
    for i in range(0, len(matrix)):
        for j in range(0, len(matrix)):
            # pass
            text = ax.text(i, j, matrix[i][j],
                           ha="center", va="center", color="w")
    # ax.set_title(file_name)
    # plt.gca().set_aspect('auto')
    # fig.tight_layout()
    plt.savefig(RESULT_FOLDER + file_name + ".png")


def plot_solutions(solution_list, file_name="run-history.png"):
    y_axis = [solution.value for solution in solution_list]
    x_axis = [i for i in range(0, len(y_axis))]
    fig = plt.figure(figsize=(16, 8))
    plt.ylim([650, 750])
    plt.plot(x_axis, y_axis, marker='o', linestyle='--', color='r', )
    plt.xlabel('iteration')
    plt.ylabel('objective function')
    fig.savefig(RESULT_FOLDER + file_name)


def plot_scatter(solution_list, file_name="scatter-plot.png"):
    rng = np.random.RandomState(0)
    values = [solution.value for solution in solution_list]
    counter = collections.Counter(values)
    y_axis = list(counter.keys())
    x_axis = range(len(counter.values()))
    counter_values = list(counter.values())
    size = [(8 * value) ** 2 for value in counter_values]
    counter_values_labels = [str(counter_value / 20 * 100) + "%" for counter_value in counter_values]
    colors = rng.rand(len(x_axis))
    fig, ax = plt.subplots(len(counter_values), sharex=True, figsize=(200, 400))
    ax.scatter(x_axis, y_axis, c=colors, s=size, alpha=0.3,
               cmap='viridis')
    for i, txt in enumerate(counter_values_labels):
        ax.annotate(txt, (x_axis[i], y_axis[i]))

    plt.ylabel('objective function')
    fig.savefig(RESULT_FOLDER + file_name)


def find_statistic_properties(solution_list, best_value):
    solution_list_size = len(solution_list)
    values = [solution.value for solution in solution_list]
    best_value = best_value if best_value is not None else min(solution_list).value
    best_value_frequency = round(sum([1 for value in values if value == best_value]) / \
                                 solution_list_size * 100, 2)
    mean_value = round(statistics.mean(values), 2)
    standard_deviation = round(statistics.stdev(values), 2)
    relative_deviation = round(standard_deviation / mean_value * 100, 3)
    return best_value, best_value_frequency, mean_value, standard_deviation, relative_deviation
