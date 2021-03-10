import matplotlib.pyplot as plt
import numpy as np

plt.style.use("seaborn")
RESULT_FOLDER = "results/"


def read_from_resources(file_name: str) -> np.array:
    input = np.loadtxt("resources/" + file_name)
    return input


def plot_solutions(y_axis, file_name="run-history.png"):
    x_axis = [i for i in range(0, len(y_axis))]
    fig = plt.figure(figsize=(16, 8))
    # plt.ylim([650, 750])
    plt.plot(
        x_axis,
        y_axis,
        marker="o",
        linestyle="--",
        color="r",
    )
    plt.xlabel("iteration")
    plt.ylabel("objective function")
    fig.savefig(RESULT_FOLDER + file_name)
