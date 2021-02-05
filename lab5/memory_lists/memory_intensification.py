from criterias.intensification_criteria import MemoryCriteria
from memory_lists.memory import Memory


class Intensification(Memory):

    def __init__(self, size, criteria_threshold_value=50, criteria=MemoryCriteria, memory_frozen_dict=None):
        super().__init__(size, criteria_threshold_value, criteria, memory_frozen_dict)
        if memory_frozen_dict is None:
            memory_frozen_dict = {}

    def __add(self, pairs):
        for pair in pairs:
            value = pair[0]
            value_position = pair[1]
            # print("value", value, "value_position", value_position, "previous", self.matrix[value][value_position])
            self.matrix[value][value_position] += 1
            self.matrix[value_position][value] += 1
            is_satisfied = self.is_criteria(self.frozen_values, number=self.matrix[value][value_position],
                                            value_pair=pair)
        print("self.frozen", self.frozen_values)

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        # print("picked_value", picked_value)
        self.__add(picked_value)