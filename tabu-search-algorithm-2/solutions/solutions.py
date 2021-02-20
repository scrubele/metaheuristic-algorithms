import random
from copy import deepcopy
from filecmp import cmp


class IntegerRepresentation:

    def __init__(self):
        self.encoding = []
        self.value = 0.0


class TravelerSalesmanProblemSolution(IntegerRepresentation):

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

    def generate_neighbour(self):
        current_solution = deepcopy(self.encoding)
        random.shuffle(current_solution)
        return current_solution

    def generate_random_neighbour(self):
        return self.__class__(weights=self.weights, encoding=self.generate_neighbour())

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

    def swap_values(self, swap_first, swap_last):
        current_encoding = deepcopy(self.encoding)
        temp = current_encoding[swap_first]
        current_encoding[swap_first] = current_encoding[swap_last]
        current_encoding[swap_last] = temp
        current_solution = self.__class__(weights=self.weights, encoding=current_encoding, swap=(swap_first, swap_last))
        return current_solution

    def set_value(self, swap_first_pos, swap_first_value):
        # print("current", self.encoding)
        # print("current", list(sorted(self.encoding)))
        current_encoding = deepcopy(self.encoding)
        # print("Swap_first_pos", swap_first_pos, "Swap_first_value", swap_first_value)
        swap_last_value = current_encoding[swap_first_pos]
        swap_last_pos = [i for i in range(0, len(current_encoding)) if current_encoding[i] == swap_first_value][0]

        # print("swap_last_pos", swap_last_pos, "swap_last_value", swap_last_value)

        if swap_last_value is not swap_first_value:
            current_encoding[swap_first_pos] = swap_first_value
            current_encoding[swap_last_pos] = swap_last_value
            # print("is not", current_encoding[swap_first_pos], current_encoding[swap_last_pos])
            encoding_size = len(current_encoding)
            unique_ones = len(set(current_encoding))
            # print(current_encoding)
            if encoding_size == unique_ones:
                current_solution = self.__class__(weights=self.weights, encoding=current_encoding,
                                                  swap=(swap_first_pos, swap_first_value))
                return current_solution
        return None

    def get_size(self) -> int:
        return len(self.encoding)

    def __str__(self):
        return "Encoding: " + str(self.encoding) + "|" + " value:" + str(self.value)

    def __repr__(self):
        return "Encoding: " + str(self.encoding) + "|" + " value:" + str(self.value)

    def __eq__(self, other):
        if not isinstance(other, TravelerSalesmanProblemSolution):
            # don't attempt to compare against unrelated types
            return NotImplemented
        return self.encoding == other.encoding and self.value == other.value

    def __key(self):
        return self.value

    def __hash__(self):
        return hash(self.__key())

    def __cmp__(self, other):
        return cmp(self.value, other.value)

    def __lt__(self, other):
        return self.value < other.value

    def __gt__(self, other):
        return self.value > other.value

    def __le__(self, other):
        return self.value <= other.value

    def __ge__(self, other):
        return self.value >= other.value
