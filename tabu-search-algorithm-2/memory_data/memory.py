from criterias.intensification_criteria import MemoryCriteria
from strategies.memory_strategies import MemoryStrategy


class Memory:

    def __init__(self, size, threshold_value=20, criteria=MemoryCriteria):
        self.size = size
        self.matrix = self.initialize_matrix()
        self.memory_strategy = MemoryStrategy.SAVE_VALUE_POSITIONS
        self.criteria = criteria(threshold_value=threshold_value)
        self.frozen_values = {}

    def initialize_matrix(self):
        return [[0 for col in range(self.size)] for row in range(self.size)]

    def __repr__(self):
        str = "\n"
        for row in self.matrix:
            for val in row:
                str += '{:4}'.format(val)
            str += "\n"
        return str

    def is_criteria(self, number, value_pair):
        is_satisfied = self.criteria.is_satisfied(value=number)
        if is_satisfied:
            value = value_pair[0]
            value_position = value_pair[1]
            if value_position in dict:
                existed_value = dict[value_position][1]
                if value > existed_value:
                    value = existed_value
            dict[value_position] = (value, number)
            self.merge_keys(dict, value, value_position, number)
        return is_satisfied

    def merge_keys(self, dict, value, value_position, number):
        existed_values = [(curr_value, number) for curr_value, number in dict.values() if curr_value
                          == value]
        if len(existed_values):
            if existed_values[0] > (value, number):
                dict.pop(value_position)
                # print("popped")
            elif existed_values[0] < (value, number):
                existed_key = list(dict.keys())[list(dict.values()).index(
                    existed_values[0])]
                dict.pop(existed_key)
            elif len(existed_values) > 1:
                dict.pop(value_position)
