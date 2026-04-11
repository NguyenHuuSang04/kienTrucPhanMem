from celery.result import AsyncResult
from django.http import JsonResponse

from .tasks import process_background_job


def health(request):
    return JsonResponse({"status": "ok", "service": "django"})


def run_task(request):
    value = request.GET.get("value", "demo")
    task = process_background_job.delay(value)
    return JsonResponse({"task_id": task.id, "state": task.state, "value": value})


def task_status(request, task_id):
    task_result = AsyncResult(task_id)
    response = {"task_id": task_id, "state": task_result.state}
    if task_result.ready():
        response["result"] = task_result.result
    return JsonResponse(response)
