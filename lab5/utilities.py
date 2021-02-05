import numpy as np
import matplotlib.pyplot as plt



def read_from_resources(file_name: str) -> np.array:
    input = np.loadtxt("resources/" + file_name)
    return input


def plot_matrix(file_name, matrix):
    # matrix = matrix.astype(float)
    plt.imshow(matrix, cmap='viridis', interpolation='nearest')
    plt.savefig(file_name)

