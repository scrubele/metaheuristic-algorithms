from criterias.intensification_criteria import MemoryCriteria
from memory_data.memory import Memory


class Intensification(Memory):

    def __init__(self, size, threshold_number, criteria_threshold_value=50, pick_number=3, criteria=MemoryCriteria):
        super().__init__(size, criteria_threshold_value, criteria)
        self.matrix = self.initialize_matrix()
        self.unsuccess_iterations = 0
        self.threshold_number = threshold_number
        self.erase_table_iteration = 100

    def __add(self, pairs):
        for value, value_position in pairs:
            # print("value", value, "value_position", value_position, "previous", self.matrix[value][value_position])
            self.matrix[value][value_position] += 1
            self.matrix[value_position][value] += 1
            # print(value, value_position, self.matrix[value][value_position], is_criteria)

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        self.__add(picked_value)

    def run(self, best_value, iteration, pick_number=10):
        # print('here', self.unsuccess_iterations, self.threshold_number )
        self.frozen_values = {}
        if self.unsuccess_iterations > self.threshold_number:
            # print("find_the_best_values", best_value)
            self.find_the_best_values(best_value.encoding, iteration, pick_number)
            self.unsuccess_iterations = 0
        # if iteration % self.erase_table_iteration == 0:
        #     self.matrix = self.initialize_matrix()

    def find_the_best_values(self, best_value, iteration=25, pick_number=3):
        # print("base_frozen_values", self.frozen_values)
        for position in range(0, len(best_value)):
            item = best_value[position]
            value = self.matrix[position][item]
            self.frozen_values[position] = (item, value)
            # smallest_dict = deepcopy(self.hidden_list)
        # smallest_dict = self.pick_all_zeros(smallest_dict)
        biggest_values = dict(sorted(self.frozen_values.items(), key=lambda item: item[1][1], reverse=True)[
                              :pick_number])
        # print(biggest_values)
        # biggest_values = {key: value for key, value in biggest_values.items() if value[1] >= }
        self.frozen_values = biggest_values
        # print("Picked values by the intensification run:", biggest_values)
        # self.should_pick_values = deepcopy(smallest_dict)
