from enum import Enum, auto


class MemoryStrategy(Enum):
    SAVE_WHOLE_ENCODING = auto()
    SAVE_IDS = auto()
    SAVE_POSITIONS = auto()

    def pick(self, solution):
        if self.value == self.SAVE_WHOLE_ENCODING.value:
            return solution.encoding
        elif self.value == self.SAVE_POSITIONS.value:
            return solution.swap
        elif self.value == self.SAVE_IDS.value:
            swap = solution.swap
            first_value = solution.encoding[swap[0]]
            last_value = solution.encoding[swap[1]]
            print(swap, first_value, last_value)
            return (first_value, last_value)
