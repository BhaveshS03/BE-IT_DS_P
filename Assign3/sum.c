#include <stdio.h>
#include "mpi.h"

int main(int argc, char *argv[])
{
    int rank, size;
    int num[20];

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    // Initialize array from 1 to 20
    for(int i = 0; i < 20; i++)
    {
        num[i] = i + 1;
    }

    // MASTER PROCESS
    if(rank == 0)
    {
        int partial_sum[4];

        printf("Distribution at rank %d\n", rank);

        // Send 5 elements to each worker process
        for(int i = 1; i < 4; i++)
        {
            MPI_Send(&num[i * 5], 5, MPI_INT, i, 1, MPI_COMM_WORLD);
        }

        // Calculate local sum for first 5 elements
        int local_sum = 0;

        for(int i = 0; i < 5; i++)
        {
            local_sum += num[i];
        }

        printf("Local sum at rank %d is %d\n", rank, local_sum);

        // Receive partial sums from workers
        for(int i = 1; i < 4; i++)
        {
            MPI_Recv(&partial_sum[i], 1, MPI_INT, i, 1,
                     MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        // Final sum calculation
        int final_sum = local_sum;

        for(int i = 1; i < 4; i++)
        {
            final_sum += partial_sum[i];
        }

        printf("Final Sum = %d\n", final_sum);
    }

    // WORKER PROCESSES
    else
    {
        int k[5];

        // Receive 5 elements from master
        MPI_Recv(k, 5, MPI_INT, 0, 1,
                 MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        // Calculate local sum
        int local_sum = 0;

        for(int i = 0; i < 5; i++)
        {
            local_sum += k[i];
        }

        printf("Local sum at rank %d is %d\n", rank, local_sum);

        // Send local sum back to master
        MPI_Send(&local_sum, 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
    }

    MPI_Finalize();

    return 0;
}
