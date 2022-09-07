# ToDo REST Service
<!-- ABOUT THE PROJECT -->
## About The Project
Complex Spring REST service that will help you manage tasks between your team members 
* Registration and authorization
* Creating ToDo with multiple tasks
* Managing task state and priority
* Adding collaborators from your team

<!-- DEPENDENCIES -->
## Dependencies
This project uses: 
* `MapStruct` for mapping dto
* `JJWT` for generating and verifying token
* `Lombok`

<!-- REST API RESOURCES -->
## REST API

Resource list:
* `/api/auth`
* `/api/roles`
* `/api/states`
* `/api/priorities`
* `/api/users/{user-id}/todos`
* `/api/users/{user-id}/todos/{todo-id}/tasks`
* `/api/users/{user-id}/todos/{todo-id}/collaborators`