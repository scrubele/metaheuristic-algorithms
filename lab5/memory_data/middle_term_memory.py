from criterias.intensification_criteria import MemoryCriteria
from memory_data.memory import Memory


class Intensification(Memory):

    def __init__(self, size, criteria_threshold_value=50, criteria=MemoryCriteria):
        super().__init__(size, criteria_threshold_value, criteria)

    def __add(self, pairs):
        for value, value_position in pairs:
            # print("value", value, "value_position", value_position, "previous", self.matrix[value][value_position])
            self.matrix[value][value_position] += 1
            self.matrix[value_position][value] += 1
            self.is_criteria(self.frozen_values, number=self.matrix[value][value_position],
                             value_pair=(value, value_position))
        print("Frozen values: ", self.frozen_values)

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        self.__add(picked_value)
