import math
import random
from copy import deepcopy
from enum import auto, Enum


class NeighbourhoodGenerationStrategy(Enum):
    LINEAR_SWAP = auto()
    TWO_OPT = auto()
    SWAP = auto()

    def generate_2opt_neighbours(self, initial_solution, frozen_values, long_memory, should_pick_values=None):
        neighbour_solutions = []
        if should_pick_values is None or len(should_pick_values) == 0:
            for swap_first in range(0, initial_solution.solution_size - 3):
                for swap_last in range(swap_first + 1, initial_solution.solution_size):
                    current_neighbour = initial_solution.generate_neighbour_solution(swap_first, swap_last)
                    if (swap_first, swap_last) not in frozen_values:
                        long_memory.add(solution=current_neighbour)
                        neighbour_solutions.append(current_neighbour)
                        # print(current_neighbour)
                    else:
                        pass
        else:
            current_neighbour_1 = deepcopy(initial_solution)
            for swap_first_position, swap_first in should_pick_values:
                # print("swap_first_position, swap_first", swap_first_position, swap_first)
                current_neighbour = initial_solution.set_value(swap_first_position, swap_first)
                # print(current_neighbour)
                if current_neighbour_1 is not None:
                    current_neighbour_1 = current_neighbour_1.set_value(swap_first_position, swap_first)
                if current_neighbour is not None:
                    long_memory.add(solution=current_neighbour)
                    neighbour_solutions.append(current_neighbour)
                if current_neighbour_1 is not None:
                    long_memory.add(solution=current_neighbour_1)
                    neighbour_solutions.append(current_neighbour_1)
        return neighbour_solutions

    def generate_linear_swap_neighbours(self, initial_solution, frozen_values, should_pick_values=None):
        neighbour_solutions = []
        for swap_first_pos in range(0, initial_solution.solution_size - 2):
            for swap_last_pos in range(swap_first_pos + 1, initial_solution.solution_size - 1):
                current_neighbour = initial_solution.swap_values(swap_first_pos, swap_last_pos)
                swap_first_value = current_neighbour.encoding[swap_first_pos]
                swap_last_value = current_neighbour.encoding[swap_last_pos]
                if (swap_first_pos, swap_first_value) not in frozen_values and (swap_last_pos, swap_last_value):
                    neighbour_solutions.append(current_neighbour)
                else:
                    pass
        return neighbour_solutions

    def generate_swap_neighbours(self, initial_solution, frozen_values, should_pick_values=None):
        neighbour_solutions = []
        if should_pick_values is None or len(should_pick_values) == 0:
            for iter in range(0, initial_solution.solution_size * int(math.sqrt(initial_solution.solution_size))):
                swap_last_pos, swap_first_pos = random.sample(range(0, initial_solution.solution_size - 1), 2)
                current_neighbour = initial_solution.swap_values(swap_first_pos, swap_last_pos)
                swap_first_value = current_neighbour.encoding[swap_first_pos]
                swap_last_value = current_neighbour.encoding[swap_last_pos]
                if (swap_first_pos, swap_first_value) not in frozen_values and (swap_last_pos, swap_last_value):
                    neighbour_solutions.append(current_neighbour)
                else:
                    pass
        else:
            for swap_first_position, swap_first in should_pick_values:
                current_neighbour = initial_solution.set_value(swap_first_position, swap_first)
                if current_neighbour is not None:
                    neighbour_solutions.append(current_neighbour)
        return neighbour_solutions

    def generate_neighbours(self, initial_solution, frozen_values, long_memory, should_pick_values=None):
        if self.value == NeighbourhoodGenerationStrategy.SWAP.value:
            return self.generate_swap_neighbours(initial_solution, frozen_values, should_pick_values)
        elif self.value == NeighbourhoodGenerationStrategy.LINEAR_SWAP.value:
            return self.generate_linear_swap_neighbours(initial_solution, frozen_values, should_pick_values)
        elif self.value == NeighbourhoodGenerationStrategy.TWO_OPT.value:
            return self.generate_2opt_neighbours(initial_solution, frozen_values, long_memory, should_pick_values)
