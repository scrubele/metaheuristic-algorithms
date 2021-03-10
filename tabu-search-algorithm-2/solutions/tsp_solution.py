import random
from copy import deepcopy

from solutions.base_solution import IntegerRepresentation


class TravellingSalesmanProblemSolution(IntegerRepresentation):

    def __init__(self, weights, encoding=None, swap=()):
        super().__init__()
        self.weights = weights
        self.solution_size = len(self.weights)
        self.encoding = encoding if encoding is not None else self.initialize_solution()
        self.get_convergence(weights=weights)
        self.swap = swap

    def initialize_solution(self):
        solution = [i for i in range(0, self.solution_size)]
        random.shuffle(solution)
        return solution

    def get_convergence(self, weights, solution=None) -> float:
        solution_weight = 0
        current_solution = solution if solution is not None else self.encoding
        current_solution = deepcopy(current_solution) + [current_solution[0]]
        for previous_index in range(0, len(current_solution) - 1):
            city_from = current_solution[previous_index]
            city_to = current_solution[previous_index + 1]
            weight = weights[city_from][city_to]
            # print("weight", weight, "city_from", city_from, "city_to", city_to)
            solution_weight += weight
        self.value = solution_weight
        return solution_weight

    def generate_random_neighbour(self):
        current_encoding = deepcopy(self.encoding)
        random.shuffle(current_encoding)
        return self.__class__(weights=self.weights, encoding=current_encoding)

    def generate_neighbour_solution(self, start_item, last_item):
        current_encoding = deepcopy(self.encoding)
        if (last_item - start_item) > 0:
            # print("start_item", start_item, "last_item", last_item)
            beginning = current_encoding[0:start_item]
            sub_list = current_encoding[start_item:last_item + 1][::-1]
            ending = current_encoding[last_item + 1:len(current_encoding)]
            # print("current_encoding", current_encoding, beginning, sub_list, en/ding)
            current_encoding = beginning + sub_list + ending
        current_solution = self.__class__(weights=self.weights, encoding=current_encoding, swap=(start_item, last_item))
        return current_solution

    def swap_encoding_items_by_positions(self, swap_first, swap_last):
        current_encoding = deepcopy(self.encoding)
        temp = current_encoding[swap_first]
        current_encoding[swap_first] = current_encoding[swap_last]
        current_encoding[swap_last] = temp
        current_solution = self.__class__(weights=self.weights, encoding=current_encoding, swap=(swap_first, swap_last))
        return current_solution

    def swap_encoding_items_by_first_position(self, swap_first_pos, swap_first_value):
        current_encoding = deepcopy(self.encoding)
        swap_last_value = current_encoding[swap_first_pos]
        swap_last_pos = [pos for pos in range(0, len(current_encoding)) if current_encoding[pos] == swap_first_value][0]
        if swap_last_value is not swap_first_value:
            current_encoding[swap_first_pos] = swap_first_value
            current_encoding[swap_last_pos] = swap_last_value
            current_solution = self.__class__(weights=self.weights, encoding=current_encoding,
                                              swap=(swap_first_pos, swap_first_value))
            return current_solution
        return None
