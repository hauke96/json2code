# JSON
```json
{
	"schemes":
	[
		{
			"name": "Train",
			"properties":
			[
				{
					"name": "ModelName",
					"type": "string",
					"constant": true,
					"collection": false
				},
				{
					"name": "Passengers",
					"type": "Person",
					"constant": true,
					"collection": true
				}
			]
		},
		{
			"name": "Person",
			"properties":
				[
				{
					"name": "Name",
					"type": "string",
					"constant": true,
					"collection": false
				},
				{
					"name": "Age",
					"type": "int",
					"constant": false,
					"collection": false
				}
			]
		}
	]
}
```
# go struct

```go
package main

// Types
type Train struct{
	modelName string `json:"ModelName"`
	passengers []Person `json:"Passengers"`
}
type Person struct{
	name string `json:"Name"`
	Age int `json:"Age"`
}

// Creators
func NewTrain(modelName string, passengers []Person){
	return Train{modelName: modelName, passengers: passengers}
}
func NewPerson(name string, age int){
	return Person{name: name, Age: age}
}

// Functions
func (t Train) getModelName() string {
	return t.modelName
}
func (t Train) gePassengers() []Person {
	return t.passengers
}
func (p Person) getModelName() string {
	return p.name
}
```

# Java class
```java
public class Train
{
	private final String ModelName;
	private final List<Person> Passengers;

	// For GSON support
	public Train() {}

	public Train(String modelName, List<Person> passengers){
		ModelName = modelName;
		Passengers = passengers;
	}

	public String       getModelName()  { return ModelName; }
	public List<Person> getPassengers() { return Passengers; }
}
```
```java
public class Person
{
	private final String Name;
	private final int Age;

	// For GSON support
	public Person() {}

	public Person(String name, int age){
		Name = name;
		Age = age;
	}

	public String getName() { return Name; }
	public int    getAge()  { return Age; }
}
```
