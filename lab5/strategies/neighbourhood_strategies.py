import math
import random
from enum import auto, Enum


class NeighbourhoodGenerationStrategy(Enum):
    LINEAR_SWAP = auto()
    TWO_OPT = auto()
    SWAP = auto()

    def generate_opt2_neighbours(self, initial_solution):
        neighbour_solutions = []
        for swap_first in range(0, initial_solution.solution_size - 3):
            for swap_last in range(swap_first + 1, initial_solution.solution_size):
                current_neighbour = initial_solution.generate_neighbour_solution(swap_first, swap_last)
                neighbour_solutions.append(current_neighbour)
                # print("current_neighbour", current_neighbour)
        return neighbour_solutions

    def generate_linear_swap_neighbours(self, initial_solution):
        neighbour_solutions = []
        for swap_first in range(0, initial_solution.solution_size - 2):
            for swap_last in range(swap_first + 1, initial_solution.solution_size - 1):
                current_neighbour = initial_solution.swap_values(swap_first, swap_last)
                neighbour_solutions.append(current_neighbour)
                # print("current_neighbour", current_neighbour)
        return neighbour_solutions

    def generate_swap_neighbours(self, initial_solution):
        neighbour_solutions = []
        for iter in range(0, initial_solution.solution_size*int(math.sqrt(initial_solution.solution_size))):
            swap_last, swap_first = random.sample(range(0, initial_solution.solution_size-1), 2)
            current_neighbour = initial_solution.swap_values(swap_first, swap_last)
            neighbour_solutions.append(current_neighbour)
            # print("current_neighbour", current_neighbour)
        print("current", len(neighbour_solutions))
        return neighbour_solutions

    def generate_neighbours(self, initial_solution):
        # print(self.value)
        if self.value == NeighbourhoodGenerationStrategy.SWAP.value:
            return self.generate_swap_neighbours(initial_solution)
        elif self.value == NeighbourhoodGenerationStrategy.LINEAR_SWAP.value:
            return self.generate_linear_swap_neighbours()
        elif self.value == NeighbourhoodGenerationStrategy.TWO_OPT:
            return self.generate_opt2_neighbours(initial_solution)
