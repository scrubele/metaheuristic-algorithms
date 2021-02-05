from copy import deepcopy

from criterias.intensification_criteria import MemoryCriteria
from memory_data.memory import Memory


class Diversification(Memory):

    def __init__(self, size, threshold_number, pick_number=3, criteria_threshold_value=0, criteria=MemoryCriteria,
                 ):
        super().__init__(size, threshold_value=criteria_threshold_value, criteria=criteria,
                         )
        self.threshold_number = threshold_number
        self.pick_number = pick_number
        self.value = 0
        self.hidden_list = {}

    def run(self, intensification):
        if self.value > self.threshold_number:
            # print("threshold")
            self.find_the_smallest_values()
            intensification.frozen_values = self.frozen_values
            self.value = 0

    def __add(self, pairs):
        for pair in pairs:
            value = pair[0]
            value_position = pair[1]
            # print("value", value, "value_position", value_position, "previous", self.matrix[value][value_position])
            self.matrix[value][value_position] += 1
            self.matrix[value_position][value] += 1
            self.hidden_list[value_position] = (value, self.matrix[value][value_position])

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        self.__add(picked_value)

    def pick_all_zeros(self, dict):
        for i in range(0, len(self.matrix) - 1):
            for j in range(i, len(self.matrix) - 1):
                if self.matrix[i][j] == 0:
                    dict[i] = (j, 0)
                    break
        return dict

    def find_the_smallest_values(self, k=3):
        smallest_dict = deepcopy(self.hidden_list)
        smallest_dict = self.pick_all_zeros(smallest_dict)
        smallest_dict = dict(sorted(smallest_dict.items(), key=lambda item: item[1][1])[:self.pick_number])
        print("Picked values by the diversification run:", smallest_dict)
        self.frozen_values = deepcopy(smallest_dict)