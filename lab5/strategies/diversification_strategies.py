from copy import deepcopy
from enum import Enum, auto


class DiversificationStrategy(Enum):
    RESTART = auto()
    CONTINUOUS = auto()

    def move(self, hidden_list, pick_number=3):
        if self.value == self.RESTART.value:
            return self.move_to_restart()
        elif self.value == self.CONTINUOUS.value:
            return self.move_to_the_smallest_values(hidden_list, pick_number)

    def move_to_smallest_values(self, hidden_list, pick_number=3):
        pass

    def move_to_the_smallest_values(self, hidden_list, pick_number=3):
        smallest_dict = deepcopy(hidden_list)
        smallest_dict = self.pick_all_zeros(smallest_dict)
        smallest_dict = dict(sorted(smallest_dict.items(), key=lambda item: item[1][1])[:pick_number])
        print("Picked values by the diversification run:", smallest_dict)
        return deepcopy(smallest_dict)

    def pick_all_zeros(self, dict):
        for i in range(0, len(self.matrix) - 1):
            for j in range(i, len(self.matrix) - 1):
                if self.matrix[i][j] == 0:
                    dict[i] = (j, 0)
                    break
        return dict
