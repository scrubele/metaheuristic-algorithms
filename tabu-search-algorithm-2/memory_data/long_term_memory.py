import random
from copy import deepcopy

from numpy import array

from criterias.intensification_criteria import MemoryCriteria
from memory_data.memory import Memory


class Diversification(Memory):

    def __init__(self, size, threshold_number, pick_number=3, criteria_threshold_value=0, criteria=MemoryCriteria):
        super().__init__(size, threshold_value=criteria_threshold_value, criteria=criteria)
        self.matrix = self.initialize_matrix()
        self.threshold_number = threshold_number
        self.pick_number = pick_number
        self.should_pick_values = {}
        self.unsuccess_iterations = 0
        self.erase_table_iteration = 100
        self.alpha = 0
        self.hidden_list = {}

    def run(self, best_value, iteration, pick_number=10):
        if self.unsuccess_iterations > self.threshold_number:
            self.find_the_smallest_values(best_value.encoding, iteration, pick_number)
            self.unsuccess_iterations = 0
        # print(iteration, iteration % self.erase_table_iteration == 0)
        if iteration % self.erase_table_iteration == 0:
            self.matrix = self.initialize_matrix()
        return best_value

    def __add(self, pairs):
        for value, value_position in pairs:
            # print("value", value, "value_position", value_position, "previous", self.matrix[value][value_position])
            self.matrix[value][value_position] += 1
            self.matrix[value][value_position] = self.matrix[value][value_position] + int(self.alpha)
            self.matrix[value_position][value] = self.matrix[value_position][value] + int(self.alpha)
            self.matrix[value_position][value] += 1
            self.hidden_list[value_position] = (value, self.matrix[value][value_position])
            # print(value, value_position, self.matrix[value][value_position], "diversification")

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        # print(picked_value)
        self.__add(picked_value)

    def pick_all_zeros(self, dict):
        for i in range(0, len(self.matrix) - 1):
            for j in range(i, len(self.matrix) - 1):
                if self.matrix[i][j] == 0:
                    dict[i] = (j, 0)
                    break
        return dict

    def find_the_smallest_values(self, best_value, iteration, pick_number=10, k=3):
        smallest_dict = deepcopy(self.hidden_list)
        # smallest_dict = self.pick_all_zeros(smallest_dict)
        mean_value = array([smallest_dict[k][1] for k in smallest_dict]).mean()
        smallest_dict = dict([(k, v) for k, v in smallest_dict.items() if v[1] < mean_value])
        l = list(smallest_dict.items())
        random.shuffle(l)
        smallest_dict = dict(l[:self.pick_number])
        # smallest_dict = dict(sorted(smallest_dict.items(), key=lambda item: item[1][1])[:self.pick_number])
        # smallest_dict = dict(sorted(self.frozen_values.items(), key=lambda item: item[1][1])[
        #                       :pick_number])
        # print("Picked values by the diversification run:", smallest_dict)
        self.should_pick_values = deepcopy(smallest_dict)

        # # print("base_frozen_values", self.frozen_values)
        # for position in range(0, len(best_value)):
        #     item = best_value[position]
        #     value = self.matrix[position][item]
        #     self.frozen_values[position] = (item, value)
        #     # smallest_dict = deepcopy(self.hidden_list)
        # # smallest_dict = self.pick_all_zeros(smallest_dict)
        # smallest_dict = dict(sorted(self.frozen_values.items(), key=lambda item: item[1][1])[
        #                       :pick_number])
        # print(smallest_dict)
        # # biggest_values = {key: value for key, value in smallest_dict.items() if value[1] <= iteration/5}
        # print("Picked values by the diversification run:", smallest_dict)
        # self.should_pick_values = deepcopy(smallest_dict)
