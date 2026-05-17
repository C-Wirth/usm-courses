import torch.nn as nn
import torch.nn.functional as F

class CustomDNN(nn.Module):
    def __init__(self):
        super(CustomDNN, self).__init__()  
        self.fc1 = nn.Linear(16, 64) # There are 16 predictor features
        self.fc2 = nn.Linear(64, 64)
        self.fc3 = nn.Linear(64, 64)
        self.fc4 = nn.Linear(64, 64)
        self.fc5 = nn.Linear(64, 3) #there are 3 output layers

    def forward(self, x):
        x = F.relu(self.fc1(x))
        x = F.softmax(self.fc2(x))
        x = F.softmax(self.fc3(x))
        x = F.softmax(self.fc4(x))
        x = self.fc5(x)
        return x
