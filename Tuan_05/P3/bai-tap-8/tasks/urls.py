from django.urls import path

from . import views

urlpatterns = [
    path("", views.health, name="health"),
    path("run-task/", views.run_task, name="run_task"),
    path("task-status/<str:task_id>/", views.task_status, name="task_status"),
]
