import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader
from CustomDataSet import CustomDataSet
from CustomDNN import CustomDNN

BATCH_SIZE = 8
EPOCHS = 250

# Device configuration
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

# Initialize the model, set loss function and optimizer
model = CustomDNN().to(device)
criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(model.parameters(), lr=0.001)

# Prepare data loaders
train_dataset = CustomDataSet('data/gym_members_exercise_tracking.csv', split='train')
val_dataset = CustomDataSet('data/gym_members_exercise_tracking.csv', split='validate')
test_dataset = CustomDataSet('data/gym_members_exercise_tracking.csv', split='test')

train_loader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True)
val_loader = DataLoader(val_dataset, batch_size=BATCH_SIZE, shuffle=True)
test_loader = DataLoader(test_dataset, batch_size=BATCH_SIZE, shuffle=True)

# Training loop
num_epochs = EPOCHS
for epoch in range(num_epochs):
    model.train()
    for features, labels in train_loader:
        features = features.to(device)
        labels = labels.to(device)
        #clean gradient for every batch
        optimizer.zero_grad()

        # Forward pass
        outputs = model(features)
        loss = criterion(outputs, labels)
        
        # Backward and optimize
        loss.backward()
        optimizer.step()
    
    # Validate the model after each epoch
    if epoch == EPOCHS-1 :
    # if epoch % 5 == 0 or epoch == 49 :
        model.eval()
        with torch.no_grad():
            correct = 0
            total = 0
            for features, labels in train_loader:
                features = features.to(device)
                labels = labels.to(device)
                outputs = model(features)
                _, predicted = torch.max(outputs.data, 1)
                total += labels.size(0)
                correct += (predicted == labels).sum().item()
            print(f'Epoch [{epoch+1}/{num_epochs}], Training Accuracy: {100 * correct / total:.2f}%')

            correct = 0
            total = 0
            for features, labels in val_loader:
                features = features.to(device)
                labels = labels.to(device)
                outputs = model(features)
                _, predicted = torch.max(outputs.data, 1)
                total += labels.size(0)
                correct += (predicted == labels).sum().item()
            print(f'Epoch [{epoch+1}/{num_epochs}], Validation Accuracy: {100 * correct / total:.2f}%')
            
            correct = 0
            total = 0
            for features, labels in test_loader:
                features = features.to(device)
                labels = labels.to(device)
                outputs = model(features)
                _, predicted = torch.max(outputs.data, 1)
                total += labels.size(0)
                correct += (predicted == labels).sum().item()
            print(f'Epoch [{epoch+1}/{num_epochs}], Testing Accuracy: {100 * correct / total:.2f}%')