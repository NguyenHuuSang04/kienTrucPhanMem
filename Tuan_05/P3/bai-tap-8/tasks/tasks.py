import time

from celery import shared_task


@shared_task
def process_background_job(value):
    time.sleep(5)
    return f"Task completed with value: {value}"
