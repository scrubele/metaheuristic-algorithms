threshold_value = 10


class MemoryCriteria:
    threshold_value: int

    def __init__(self, threshold_value=threshold_value):
        self.threshold_value = threshold_value

    def is_satisfied(self, value) -> bool:
        # print(value, "threshold_value", self.threshold_value)
        return value >= self.threshold_value

