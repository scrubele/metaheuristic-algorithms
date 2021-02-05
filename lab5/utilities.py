import matplotlib.pyplot as plt
import numpy as np


def read_from_resources(file_name: str) -> np.array:
    input = np.loadtxt("resources/" + file_name)
    return input


def plot_matrix(file_name, matrix):
    # matrix = matrix.astype(float)
    fig, ax = plt.subplots()
    ax.imshow(matrix, cmap='viridis', interpolation='nearest')
    # Loop over data dimensions and create text annotations.
    for i in range(0, len(matrix)):
        for j in range(0, len(matrix)):
            # print(i, j)
            text = ax.text(i, j, matrix[i] [j],
                           ha="center", va="center", color="w")
    ax.set_title(file_name)
    fig.tight_layout()
    # plt.show()
    plt.savefig("results/" + file_name +".png")


def plot_solutions(solution_list):
    y_axis = [solution.value for solution in solution_list]
    x_axis = [i for i in range(0, len(y_axis))]
    fig = plt.figure(figsize=(16, 8))
    plt.plot(x_axis, y_axis, marker='o', linestyle='--', color='r', )
    plt.xlabel('iteration')
    plt.ylabel('objective function')
    # plt.xticks(xi, x)
    fig.savefig("results/"+"legend.png")
